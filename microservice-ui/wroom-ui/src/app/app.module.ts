import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app.component';
import { MaterialModule } from './modules/shared/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { TokenInterceptor } from './modules/auth/interceptor/token.interceptor';
import { AuthGuard } from './modules/auth/guards/auth.guard';
import { AlreadyAuthenticatedGuard } from './modules/auth/guards/alreadyAuthenticated.guard';
import { CreateAdGuard } from './modules/ads/guards/create-ad.guard';
import { SharedModule } from './modules/shared/shared.module';
import { AdminGuard } from './modules/shared/guards/admin.guard';
import { DebtsComponent } from './modules/shared/components/debts/debts.component';

@NgModule({
  declarations: [
    AppComponent,
    DebtsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
    SharedModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    AuthGuard,
    AlreadyAuthenticatedGuard,
    CreateAdGuard,
    AdminGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
