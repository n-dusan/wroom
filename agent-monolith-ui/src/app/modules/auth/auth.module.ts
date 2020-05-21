import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthComponent } from './auth.component';
import { MaterialModule } from '../shared/material.module';
import { AuthRoutingModule } from './auth-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { SignupRequestSentComponent } from './components/signup-request-sent/signup-request-sent.component';
import { TokenInterceptor } from './interceptor/token.interceptor';
import { ErrorInterceptor } from './interceptor/error.interceptor';



@NgModule({
  declarations: [
    AuthComponent,
    SignupRequestSentComponent
  ],
  imports: [
    MaterialModule,
    AuthRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule
  ],
})
export class AuthModule { }
