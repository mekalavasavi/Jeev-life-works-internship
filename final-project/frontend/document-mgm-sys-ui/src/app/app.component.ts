import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DocumentListComponent } from './document-list/document-list.component';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, DocumentListComponent],
  template: `
    <h1>📂 Document Management System</h1>

    <ng-container *ngIf="!auth.isLoggedIn(); else app">
      <div class="card">
        <h3>Login</h3>
        <form (ngSubmit)="login()">
          <input [(ngModel)]="username" name="username" placeholder="Username" required />
          <input [(ngModel)]="password" name="password" type="password" placeholder="Password" required />
          <button type="submit">Login</button>
        </form>
        <small>Default: <code>dms / dms123</code></small>
      </div>
    </ng-container>

    <ng-template #app>
      <div style="text-align:right; margin-bottom: 8px;">
        <button (click)="logout()">Logout</button>
      </div>
      <app-document-list></app-document-list>
    </ng-template>
  `
})
export class AppComponent {
  username = '';
  password = '';

  constructor(public auth: AuthService) {}

  login() {
    this.auth.setCredentials(this.username, this.password);
  }

  logout() {
    this.auth.clear();
  }
}
