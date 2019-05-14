import { Component, OnInit } from '@angular/core';
import {GroupDataService} from './group-data.service';
import {GroupDataDataSource} from './group-data.datasource';
import {GroupClassificationData} from './group-data.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './group-data.component.html',
  styleUrls: ['./group-data.component.css']
})
export class GroupDataComponent implements OnInit {

  constructor(private dataService: GroupDataService, private router: Router) { }

  displayedColumns = ['creation_time', 'group_id', 'post_type', 'post_text', 'delete'];
  dataSource = new GroupDataDataSource(this.dataService);

  groupData: GroupClassificationData = new GroupClassificationData();

  submit(groupData: GroupClassificationData) {

    this.dataService.classifyGroup(groupData)
                   .subscribe(
                      (data) => {
                        console.log(data);
                        this.router.navigate(['/dashboard/group-data']);
                      },
                   error => console.error(error)
                   );

  }

  ngOnInit() { }

}
