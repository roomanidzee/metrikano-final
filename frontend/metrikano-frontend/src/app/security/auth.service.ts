import { Injectable } from '@angular/core';
import {JwtHelperService, JwtModuleOptions} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(public jwtHelper: JwtHelperService) {}

  static getToken(): string {
    return sessionStorage.getItem('token');
  }

  isAuthenticated(): Promise<boolean> {

    const token = AuthService.getToken();

    return new Promise(resolve => {

      const condition = !this.jwtHelper.isTokenExpired(token);
      resolve(condition);

    });

  }

}

export const JWTModuleOptions: JwtModuleOptions = {
  config: {
    tokenGetter: AuthService.getToken,
    whitelistedDomains: ['localhost:8089', 'auth-service:8089']
  }
};
