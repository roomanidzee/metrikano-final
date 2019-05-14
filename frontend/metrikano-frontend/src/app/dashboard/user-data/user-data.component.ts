import { Component } from '@angular/core';
import {UserDataService} from './user-data.service';
import {UserDataDataSource} from './user-data.datasource';
import {UserClassificationData} from './user-data.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-data',
  templateUrl: './user-data.component.html',
  styleUrls: ['./user-data.component.css']
})
export class UserDataComponent {

  constructor(private dataService: UserDataService, private router: Router) { }

  displayedColumns = ['record_id', 'user_id', 'user_type', 'first_name', 'last_name', 'delete'];
  dataSource = new UserDataDataSource(this.dataService);

  userData: UserClassificationData = new UserClassificationData();

  submit(userData: UserClassificationData) {

    this.dataService.classifyUser(userData)
                    .subscribe(
                       (data) => {
                         console.log(data);
                         this.router.navigate(['/dashboard/user-data']);
                       },
                      error => console.error(error)
                    );

  }

}
