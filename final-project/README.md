**Document Management System (DMS)**

1)Backend (Spring Boot Microservice)-
Key Features:
-Upload documents with metadata (title, description, category, uploadedBy, fileName, uploadedAt)
-Download documents
-Delete documents
-Preview documents in browser (PDFs/images)
-Search documents (by title, category, etc.)
-List all documents

MySQL Schema Script-
CREATE DATABASE dmsdb;
USE dmsdb;
CREATE TABLE documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    uploaded_by VARCHAR(100),
    file_name VARCHAR(255),
    file_type VARCHAR(100),
    file_size BIGINT,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data LONGBLOB
);

2. Frontend (Angular Application)-
Features-
-List all documents in card UI
-Search documents
-Upload new document with metadata
-Download documents
-Delete documents
-Preview documents (PDF/image in iframe)
-Upload progress bar

3. Deployment Instructions-
-Backend:
cd document-mgm-sys-backend
mvn spring-boot:run
-frontend:
cd document-mgm-sys-ui
npm install
ng serve --proxy-config proxy.conf.json

API Base URLs
Backend API: http://localhost:8080/api/documents
Frontend: http://localhost:4200




