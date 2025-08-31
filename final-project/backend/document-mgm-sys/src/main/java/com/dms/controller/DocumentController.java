package com.dms.controller;
import com.dms.model.Document;
import com.dms.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/documents")
@CrossOrigin(origins = "http://localhost:4200") // allow Angular app 
public class DocumentController {
    private static final String UPLOAD_DIR = "uploads/";
    @Autowired
    private DocumentRepository repo;
    // ---- Regular upload ----
    @PostMapping("/upload")
    public Document uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "uploadedBy", defaultValue = "Unknown") String uploadedBy,
            @RequestParam(value = "category", defaultValue = "General") String category
    ) throws IOException {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();
        String filePath = UPLOAD_DIR + file.getOriginalFilename();
        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes());
        Document doc = new Document();
        doc.setTitle(title);
        doc.setDescription(description);
        doc.setFileName(file.getOriginalFilename());
        doc.setFileSize(file.getSize());
        doc.setFilePath(filePath);
        doc.setUploadedBy(uploadedBy);
        doc.setUploadDate(LocalDateTime.now());
        doc.setCategory(category);
        return repo.save(doc);
    }
    // ---- Download ----
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {
        Document doc = repo.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
        Path path = Paths.get(doc.getFilePath());
        byte[] data = Files.readAllBytes(path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }
    // ---- Preview (inline) ----
    @GetMapping("/{id}/preview")
    public ResponseEntity<byte[]> previewFile(@PathVariable Long id) throws IOException {
        Document doc = repo.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
        Path path = Paths.get(doc.getFilePath());
        byte[] data = Files.readAllBytes(path);
        String mimeType = Files.probeContentType(path);
        if (mimeType == null || mimeType.isBlank()) {
            // Fallback for common types
            String name = doc.getFileName().toLowerCase();
            if (name.endsWith(".pdf")) mimeType = "application/pdf";
            else if (name.endsWith(".png")) mimeType = "image/png";
            else if (name.endsWith(".jpg") || name.endsWith(".jpeg")) mimeType = "image/jpeg";
            else if (name.endsWith(".gif")) mimeType = "image/gif";
            else mimeType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + doc.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(mimeType))
                .body(data);
    }
    // ---- List all ----
    @GetMapping
    public List<Document> getAll() {
        return repo.findAll();
    }
    // ---- Delete (file + row) ----
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.findById(id).ifPresent(doc -> {
            try {
                Files.deleteIfExists(Paths.get(doc.getFilePath()));
            } catch (IOException ignored) {}
            repo.deleteById(id);
        });
    }
    // ---- Chunk upload ----
    @PostMapping("/upload-chunk")
    public ResponseEntity<String> uploadChunk(
            @RequestParam("file") MultipartFile chunk,
            @RequestParam("fileName") String fileName,
            @RequestParam("chunkNumber") int chunkNumber
    ) throws IOException {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();
        String chunkPath = UPLOAD_DIR + fileName + ".part" + chunkNumber;
        Files.write(Paths.get(chunkPath), chunk.getBytes());
        return ResponseEntity.ok("Chunk " + chunkNumber + " uploaded");
    }
    @PostMapping("/finalize-upload")
    public ResponseEntity<String> finalizeUpload(
            @RequestParam("fileName") String fileName,
            @RequestParam("totalChunks") int totalChunks,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "uploadedBy", defaultValue = "Unknown") String uploadedBy,
            @RequestParam(value = "category", defaultValue = "General") String category
    ) throws IOException {
        String finalPath = UPLOAD_DIR + fileName;
        try (OutputStream fos = Files.newOutputStream(Paths.get(finalPath))) {
            for (int i = 1; i <= totalChunks; i++) {
                Path partPath = Paths.get(UPLOAD_DIR + fileName + ".part" + i);
                Files.copy(partPath, fos);
                Files.deleteIfExists(partPath);
            }
        }
        Document doc = new Document();
        doc.setTitle(title);
        doc.setDescription(description);
        doc.setFileName(fileName);
        doc.setFilePath(finalPath);
        doc.setFileSize(Files.size(Paths.get(finalPath)));
        doc.setUploadedBy(uploadedBy);
        doc.setUploadDate(LocalDateTime.now());
        doc.setCategory(category);
        repo.save(doc);
        return ResponseEntity.ok("Upload complete: " + fileName);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(
            @PathVariable Long id,
            @RequestBody Document updatedDoc) {
        return repo.findById(id)
                .map(doc -> {
                    doc.setTitle(updatedDoc.getTitle());
                    doc.setDescription(updatedDoc.getDescription());
                    doc.setCategory(updatedDoc.getCategory());
                    doc.setUploadedBy(updatedDoc.getUploadedBy());
                    return ResponseEntity.ok(repo.save(doc));
                })
                .orElse(ResponseEntity.notFound().build());
    }
 // ---- Get single document ----
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}