import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MaterialModule} from './material_design/material.module';
import {JwtModule} from '@auth0/angular-jwt';
import {JWTModuleOptions} from './security/auth.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MaterialModule,
    JwtModule.forRoot(JWTModuleOptions)
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
