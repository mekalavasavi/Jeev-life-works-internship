import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DocumentService, Document } from '../services/document.service';
import { FilterPipe } from '../filter.pipe';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-document-list',
  standalone: true,
  imports: [CommonModule, FormsModule, FilterPipe],
  templateUrl: './document-list.component.html'
})
export class DocumentListComponent implements OnInit, OnDestroy {
  documents: Document[] = [];
  categories: string[] = []; // unique categories
  selectedCategory = '';
  searchText = '';

  // Pagination
  currentPage = 1;
  pageSize = 5;

  newDoc: { title: string; description: string; category: string; uploadedBy: string; file?: File } = {
    title: '',
    description: '',
    category: '',
    uploadedBy: ''
  };
  uploadProgress = 0;
  previewUrl: SafeResourceUrl | null = null;
  private previewObjectUrl: string | null = null;

  editingDoc: any = null;
  editData = { title: '', description: '', category: '',uploadedBy: '' };

  constructor(private documentService: DocumentService, private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.loadDocuments();
  }

  ngOnDestroy(): void {
    this.revokeObjectUrl();
  }

  private revokeObjectUrl() {
    if (this.previewObjectUrl) {
      URL.revokeObjectURL(this.previewObjectUrl);
      this.previewObjectUrl = null;
    }
  }

  loadDocuments() {
    this.documentService.getDocuments().subscribe({
      next: (data) => {
        this.documents = data;
        this.extractCategories();
      },
      error: (err) => console.error('Error fetching documents', err),
    });
  }

  extractCategories() {
    this.categories = [...new Set(this.documents.map(doc => doc.category || 'Uncategorized'))];
  }

  filteredDocuments(): Document[] {
    return this.documents.filter(doc =>
      !this.selectedCategory || doc.category === this.selectedCategory
    );
  }

  paginatedDocs(): Document[] {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.filteredDocuments().slice(start, start + this.pageSize);
  }

  totalPages(): number {
    return Math.ceil(this.filteredDocuments().length / this.pageSize);
  }

  nextPage() {
    if (this.currentPage < this.totalPages()) this.currentPage++;
  }

  prevPage() {
    if (this.currentPage > 1) this.currentPage--;
  }

  // Upload
  onFileSelected(event: any) {
    this.newDoc.file = event.target.files[0];
  }

  uploadDocument() {
    if (!this.newDoc.title || !this.newDoc.description || !this.newDoc.file) {
      alert('Title, Description and File are required');
      return;
    }

    const file = this.newDoc.file!;
    const chunkSize = 1024 * 1024;
    const totalChunks = Math.ceil(file.size / chunkSize);
    let currentChunk = 0;
    this.uploadProgress = 0;

    const uploadNext = () => {
      const start = currentChunk * chunkSize;
      const end = Math.min(file.size, start + chunkSize);
      const blob = file.slice(start, end);

      const form = new FormData();
      form.append('file', blob);
      form.append('fileName', file.name);
      form.append('chunkNumber', (currentChunk + 1).toString());

      this.documentService.uploadChunk(form).subscribe({
        next: () => {
          currentChunk++;
          this.uploadProgress = Math.floor((currentChunk / totalChunks) * 100);

          if (currentChunk < totalChunks) {
            uploadNext();
          } else {
            const finalize = new FormData();
            finalize.append('fileName', file.name);
            finalize.append('totalChunks', totalChunks.toString());
            finalize.append('title', this.newDoc.title);
            finalize.append('description', this.newDoc.description);
            finalize.append('category', this.newDoc.category);
            finalize.append('uploadedBy', this.newDoc.uploadedBy || 'Anonymous'); 

            this.documentService.finalizeUpload(finalize).subscribe({
              next: () => {
                this.uploadProgress = 0;
                this.newDoc = { title: '', description: '', category: '',uploadedBy: '' };
                this.loadDocuments();
                alert('Upload complete!');
              },
              error: (err) => {
                console.error('Error finalizing upload', err);
                alert('Failed to finalize upload');
              }
            });
          }
        },
        error: (err) => {
          console.error('Error uploading chunk', err);
          alert('Chunk upload failed');
        }
      });
    };

    uploadNext();
  }

  // Download
  downloadDocument(id: number, fileName: string) {
    this.documentService.downloadDocument(id).subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = fileName;
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

  // Delete
  deleteDocument(id: number) {
    this.documentService.deleteDocument(id).subscribe({
      next: () => this.loadDocuments(),
      error: (err) => console.error('Error deleting document', err),
    });
  }

  // Preview
  previewDocument(id: number) {
    this.documentService.previewDocument(id).subscribe((blob) => {
      this.revokeObjectUrl();
      this.previewObjectUrl = URL.createObjectURL(blob);
      this.previewUrl = this.sanitizer.bypassSecurityTrustResourceUrl(this.previewObjectUrl);
    });
  }

  closePreview() {
    this.revokeObjectUrl();
    this.previewUrl = null;
  }

  // Edit
  startEdit(doc: any) {
    this.editingDoc = { ...doc };
    this.editData = {
      title: doc.title,
      description: doc.description,
      category: doc.category,
      uploadedBy: doc.uploadedBy || ''
    };
  }

  cancelEdit() {
    this.editingDoc = null;
  }

  saveEdit() {
    if (this.editingDoc) {
      this.documentService.updateDocument(this.editingDoc.id, this.editData)
        .subscribe(() => {
          this.loadDocuments();
          this.editingDoc = null;
        });
    }
  }
}