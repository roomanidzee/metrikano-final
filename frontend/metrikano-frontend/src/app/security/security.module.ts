import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MaterialModule} from '../material_design/material.module';
import {LoginModule} from './login/login.module';
import {RegisterModule} from './register/register.module';
import {SecurityRoutingModule} from './security-routing.module';
import {AuthService} from './auth.service';
import {AuthGuardService} from './auth-guard.service';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    LoginModule,
    RegisterModule,
    SecurityRoutingModule
  ],
  providers: [AuthService, AuthGuardService]
})
export class SecurityModule { }
