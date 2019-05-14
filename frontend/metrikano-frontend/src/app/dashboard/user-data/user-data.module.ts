import { NgModule } from '@angular/core';
import {MaterialModule} from '../../material_design/material.module';
import {UserDataComponent} from './user-data.component';
import {UserDataService} from './user-data.service';
import {MainHeaderModule} from '../../navigation/main-header/main-header.module';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    MainHeaderModule,
    HttpClientModule,
    FormsModule
  ],
  declarations: [UserDataComponent],
  providers: [UserDataService],
  exports: [UserDataComponent]
})
export class UserDataModule { }
