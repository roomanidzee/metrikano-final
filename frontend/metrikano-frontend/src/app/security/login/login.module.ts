import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import { NgModule } from '@angular/core';
import {MaterialModule} from '../../material_design/material.module';
import {LoginComponent} from './login.component';
import {RouterModule} from '@angular/router';
import {FlexLayoutModule} from '@angular/flex-layout';
import {LoginService} from './login.service';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    FlexLayoutModule
  ],
  declarations: [LoginComponent],
  providers: [LoginService],
  exports: [LoginComponent]
})
export class LoginModule { }
