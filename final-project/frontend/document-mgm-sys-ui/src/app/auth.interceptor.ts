import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const authHeader = auth.getAuthHeader();

  const isApi = req.url.startsWith('http://localhost:8080/');
  if (authHeader && isApi) {
    req = req.clone({ setHeaders: { Authorization: authHeader } });
  }

  return next(req);
};
