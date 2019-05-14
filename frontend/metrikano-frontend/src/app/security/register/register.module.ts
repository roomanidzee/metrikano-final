import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import { NgModule } from '@angular/core';
import {MaterialModule} from '../../material_design/material.module';
import {RegisterComponent} from './register.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {RegisterService} from './register.service';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    FlexLayoutModule,
    ReactiveFormsModule
  ],
  declarations: [RegisterComponent],
  providers: [RegisterService],
  exports: [RegisterComponent]
})
export class RegisterModule { }
