package com.dms.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 2000)
    private String description;
    private String fileName;
    private Long fileSize;
    private String filePath;
    private LocalDateTime uploadDate;
    private String uploadedBy;
    private String category;

    public Long getId() { 
    	return id; 
    	}
    public void setId(Long id) {
    	this.id = id;
    	}
    public String getTitle() { 
    	return title; 
    	}
    public void setTitle(String title) { 
    	this.title = title; 
    	}
    public String getDescription() { 
    	return description;
    	}
    public void setDescription(String description) { 
    	this.description = description; 
    	}
    public String getFileName() { 
    	return fileName; 
    	}
    public void setFileName(String fileName) {
    	this.fileName = fileName; 
    	}
    public Long getFileSize() { 
    	return fileSize; 
    	}
    public void setFileSize(Long fileSize) { 
    	this.fileSize = fileSize; 
    	}
    public LocalDateTime getUploadDate() { 
    	return uploadDate; 
    	}
    public void setUploadDate(LocalDateTime uploadDate) { 
    	this.uploadDate = uploadDate; 
    	}
    public String getUploadedBy() {
    	return uploadedBy;
    	}
    public void setUploadedBy(String uploadedBy) { 
    	this.uploadedBy = uploadedBy;
    	}
    public String getFilePath() {
    	return filePath; 
    	}
    public void setFilePath(String filePath) { 
    	this.filePath = filePath; 
    	}
    public String getCategory() {
    	return category; 
    	}
    public void setCategory(String category) {
    	this.category = category;
    	}
}