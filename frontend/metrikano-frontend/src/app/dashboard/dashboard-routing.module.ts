import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {GroupDataComponent} from './group-data/group-data.component';
import {MainHeaderComponent} from '../navigation/main-header/main-header.component';
import {UserDataComponent} from './user-data/user-data.component';
import {AuthGuardService as AuthGuard} from '../security/auth-guard.service';

const routes: Routes = [
  {
    path: '', component: MainHeaderComponent, children: [
      {
        path: 'group-data', component: GroupDataComponent, canActivate: [AuthGuard]
      },
      {
        path: 'user-data', component: UserDataComponent, canActivate: [AuthGuard]
      }
    ]
  }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
  ],
  declarations: [],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
