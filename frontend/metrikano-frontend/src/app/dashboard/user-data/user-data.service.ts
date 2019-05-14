import { Injectable } from '@angular/core';
import {UserClassificationData, UserData, RecordDTO} from './user-data.model';
import {Observable, of} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {map, shareReplay} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  elementData: UserData[] = [];
  recordDTOs: RecordDTO[] = [];

  constructor(private httpClient: HttpClient) { }

  getData(): Observable<UserData[]> {

    const httpHeaders = new HttpHeaders({
      'Content-Type' : 'application/json',
      'Cache-Control': 'no-cache',
       Authorization: `${sessionStorage.getItem('token')}`
    });

    const options = {
      headers: httpHeaders
    };

    const username = sessionStorage.getItem('username');

    this.httpClient.get(`${environment.dbService}/user/records/${username}`)
                   .pipe(
                     map((res: any) => res.json().map(item => {

                       if (item.record_type === 'VK_USER') {
                         this.recordDTOs.push(new RecordDTO(item.username, item.record_id, item.record_type));
                       }

                     })),
                     shareReplay()
                   );

    const recordIDs: string[] = [];

    this.recordDTOs.map(item => {
      recordIDs.push(item.recordID);
    });

    const requestBody = {
      records_ids: recordIDs
    };

    const requestString: string = encodeURIComponent(JSON.stringify(requestBody));

    this.httpClient.get(`${environment.usersService}/user/data/for_ids?${requestString}`, options)
                   .pipe(
                     map((res: any) => res.json().map(item => {
                       this.elementData.push(new UserData(item.record_id, item.user_id, item.user_type,
                                                          item.first_name, item.last_name));
                     })),
                     shareReplay()
                   );

    return of<UserData[]>(this.elementData);
  }

  dataLength() {
    return this.elementData.length;
  }

  classifyUser(userData: UserClassificationData): Observable<any> {

    const httpHeaders = new HttpHeaders({
      'Content-Type' : 'application/json',
      'Cache-Control': 'no-cache',
       Authorization: `Bearer ${sessionStorage.getItem('token')}`
    });

    const options = {
      headers: httpHeaders
    };

    return this.httpClient.post(`${environment.classificationService}/classification/users-vk/classify`, userData, options)
                          .pipe(shareReplay());

  }

}
