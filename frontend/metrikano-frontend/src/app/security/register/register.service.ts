import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RegistrationDTO} from './register.model';
import {environment} from '../../../environments/environment';
import {shareReplay} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private httpClient: HttpClient) { }

  registerUser(username: string, password: string): Observable<any> {

    const httpHeaders = new HttpHeaders({
      'Content-Type' : 'application/json',
      'Cache-Control': 'no-cache'
    });

    const options = {
      headers: httpHeaders
    };

    const registrationDTO: RegistrationDTO = new RegistrationDTO(username, password);

    return this.httpClient.post(`${environment.authService}/auth/register`, registrationDTO, options)
                          .pipe(shareReplay());

  }

}
