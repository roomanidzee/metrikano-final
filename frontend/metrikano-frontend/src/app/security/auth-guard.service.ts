import { Injectable } from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(public authService: AuthService, public router: Router) { }

  canActivate(): Promise<boolean> | boolean {

    return new Promise(resolve => {

      this.authService.isAuthenticated()
                      .then((status: boolean) => {
                        if (status === false) {
                          this.router.navigate(['security/login']);
                        }
                        resolve(status);
                      })
                      .catch(() => {
                        this.router.navigate(['security/login']);
                        resolve(false);
                      });

    });

  }

}
