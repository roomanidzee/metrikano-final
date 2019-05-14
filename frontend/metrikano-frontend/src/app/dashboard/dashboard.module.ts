import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {GroupDataModule} from './group-data/group-data.module';
import {DashboardRoutingModule} from './dashboard-routing.module';
import {MaterialModule} from '../material_design/material.module';
import {NavigationModule} from '../navigation/navigation.module';
import {UserDataModule} from './user-data/user-data.module';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    GroupDataModule,
    UserDataModule,
    NavigationModule,
    DashboardRoutingModule
  ]
})
export class DashboardModule { }
