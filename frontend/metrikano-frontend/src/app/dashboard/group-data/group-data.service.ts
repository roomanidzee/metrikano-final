import { Injectable } from '@angular/core';
import {GroupClassificationData, GroupData} from './group-data.model';
import {Observable, of} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {map, shareReplay} from 'rxjs/operators';
import {RecordDTO} from './group-data.model';

@Injectable({
  providedIn: 'root'
})
export class GroupDataService {

  elementData: GroupData[] = [];
  recordDTOs: RecordDTO[] = [];

  constructor(private httpClient: HttpClient) { }

  getData(): Observable<GroupData[]> {

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

                         if (item.record_type === 'VK_GROUP') {
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

    this.httpClient.get(`${environment.groupsService}/user/data/for_ids?${requestString}`, options)
                   .pipe(
                     map((res: any) => res.json().map(item => {
                       this.elementData.push(new GroupData(item.group_id, item.post_text, item.post_type, item.creation_time));
                     })),
                     shareReplay()
                   );

    return of<GroupData[]>(this.elementData);
  }

  dataLength() {
    return this.elementData.length;
  }

  classifyGroup(groupData: GroupClassificationData): Observable<any> {

    const httpHeaders = new HttpHeaders({
      'Content-Type' : 'application/json',
      'Cache-Control': 'no-cache',
      Authorization: `Bearer ${sessionStorage.getItem('token')}`
    });

    const options = {
      headers: httpHeaders
    };

    return this.httpClient.post(`${environment.classificationService}/classification/groups-vk/classify`, groupData, options)
                          .pipe(shareReplay());

  }

}
