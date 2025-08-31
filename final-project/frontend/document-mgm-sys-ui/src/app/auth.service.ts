import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private key = 'dms_basic_credentials';

  setCredentials(username: string, password: string) {
    const token = btoa(`${username}:${password}`);
    localStorage.setItem(this.key, token);
  }

  clear() {
    localStorage.removeItem(this.key);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem(this.key);
  }

  getAuthHeader(): string | null {
    const token = localStorage.getItem(this.key);
    return token ? `Basic ${token}` : null;
  }
}
