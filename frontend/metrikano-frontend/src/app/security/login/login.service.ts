import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, pipe} from 'rxjs';
import {environment} from '../../../environments/environment';
import {LoginDTO} from './login.model';
import {shareReplay} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient) { }

  loginUser(username: string, password: string): Observable<any> {

    const httpHeaders = new HttpHeaders({
      'Content-Type' : 'application/json',
      'Cache-Control': 'no-cache'
    });

    const options = {
      headers: httpHeaders
    };

    const loginDTO: LoginDTO = new LoginDTO(username, password);

    return this.httpClient.post(`${environment.authService}/auth/login`, loginDTO, options)
                          .pipe(shareReplay());

  }

}
