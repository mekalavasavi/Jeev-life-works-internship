
import { Pipe, PipeTransform } from '@angular/core';
import { Document } from './services/document.service';

@Pipe({
  name: 'filter',
  standalone: true
})
export class FilterPipe implements PipeTransform {
  transform(documents: Document[], searchText: string): Document[] {
    if (!documents || !searchText) return documents;
    return documents.filter(doc =>
      doc.title?.toLowerCase().includes(searchText.toLowerCase()) ||
      doc.description?.toLowerCase().includes(searchText.toLowerCase()) ||
      doc.category?.toLowerCase().includes(searchText.toLowerCase())
    );
  }
}
