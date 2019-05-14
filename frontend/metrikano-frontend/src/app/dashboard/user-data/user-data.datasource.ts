import {Injectable} from '@angular/core';
import {DataSource} from '@angular/cdk/table';
import {UserData} from './user-data.model';
import {UserDataService} from './user-data.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserDataDataSource extends DataSource<UserData> {

  constructor(private dataService: UserDataService) {
    super();
  }

  connect(collectionViewer): Observable<UserData[] | ReadonlyArray<UserData>> {
    return this.dataService.getData();
  }

  disconnect(collectionViewer): void {
  }

}
