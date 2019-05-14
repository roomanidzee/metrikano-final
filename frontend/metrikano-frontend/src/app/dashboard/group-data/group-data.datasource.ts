import { Injectable } from '@angular/core';
import {DataSource} from '@angular/cdk/table';
import {GroupData} from './group-data.model';
import {Observable} from 'rxjs';
import {GroupDataService} from './group-data.service';

@Injectable({
  providedIn: 'root'
})
export class GroupDataDataSource extends DataSource<GroupData> {

  constructor(private dataService: GroupDataService) {
    super();
  }

  connect(): Observable<GroupData[] | ReadonlyArray<GroupData>> {
    return this.dataService.getData();
  }

  disconnect(): void {}

}
