import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Document {
  id: number;
  title: string;
  description: string;
  fileName?: string;
  fileSize?: number;
  category?: string;
  uploadedBy?: string;
  uploadDate?: string;

}

@Injectable({ providedIn: 'root' })
export class DocumentService {
  private apiUrl = 'http://localhost:8080/documents';

  constructor(private http: HttpClient) {}

  getDocuments(): Observable<Document[]> {
    return this.http.get<Document[]>(this.apiUrl);
  }

  uploadDocument(formData: FormData): Observable<Document> {
    return this.http.post<Document>(`${this.apiUrl}/upload`, formData);
  }

  deleteDocument(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  downloadDocument(id: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/download/${id}`, { responseType: 'blob' });
  }

  // Chunked upload
  uploadChunk(formData: FormData): Observable<string> {
    return this.http.post(`${this.apiUrl}/upload-chunk`, formData, { responseType: 'text' });
  }

  finalizeUpload(formData: FormData): Observable<string> {
    return this.http.post(`${this.apiUrl}/finalize-upload`, formData, { responseType: 'text' });
  }

  // Preview
  previewDocument(id: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${id}/preview`, { responseType: 'blob' });
  }
  updateDocument(id: number, document: any): Observable<any> {
  return this.http.put(`${this.apiUrl}/${id}`, document);
}

}