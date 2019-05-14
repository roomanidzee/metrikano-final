import { NgModule } from '@angular/core';
import {RouterModule} from '@angular/router';
import {MainHeaderComponent} from './main-header.component';
import {MaterialModule} from '../../material_design/material.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {CommonModule} from '@angular/common';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule,
    FlexLayoutModule
  ],
  declarations: [MainHeaderComponent],
  exports: [MainHeaderComponent]
})
export class MainHeaderModule {}
