import { NgModule } from '@angular/core';
import {GroupDataComponent} from './group-data.component';
import {GroupDataService} from './group-data.service';
import {MaterialModule} from '../../material_design/material.module';
import {MainHeaderModule} from '../../navigation/main-header/main-header.module';
import {HttpClientModule} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    MainHeaderModule,
    HttpClientModule,
    FormsModule
  ],
  declarations: [GroupDataComponent],
  providers: [GroupDataService],
  exports: [GroupDataComponent]
})
export class GroupDataModule { }
