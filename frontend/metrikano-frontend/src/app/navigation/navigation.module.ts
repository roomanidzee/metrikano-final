import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {WelcomeModule} from './welcome/welcome.module';
import {NavigationRoutingModule} from './navigation-routing.module';

@NgModule({
  imports: [
    CommonModule,
    WelcomeModule,
    NavigationRoutingModule
  ]
})
export class NavigationModule { }
