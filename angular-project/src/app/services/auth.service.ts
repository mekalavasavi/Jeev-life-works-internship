import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  signup(data: any) {
    localStorage.setItem('user', JSON.stringify(data));
  }

  getUser() {
    const data = localStorage.getItem('user');
    return data ? JSON.parse(data) : null;
  }

  login(email: string, password: string): boolean {
    const user = this.getUser();
    if (user && user.email === email && user.password === password) {
      localStorage.setItem('loggedIn', 'true');
      return true;
    }
    return false;
  }

  logout() {
    localStorage.removeItem('loggedIn');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('loggedIn');
  }
}
