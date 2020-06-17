import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AngularYandexMapsModule } from 'angular8-yandex-maps';
import { ControlMessagesComponent } from './control-messages/control-messages.component';
import { HomeComponent } from './components/home/home.component';
import { MaterialModule } from './material.module';
import { EmailConfirmationComponent } from './components/email-confirmation/email-confirmation.component';
import { VehicleDetailsComponent } from '../vehicles/vehicle-details/vehicle-details.component';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { AdDetailComponent } from '../search/components/ad-detail/ad-detail.component';
import { CreateBundleDialogComponent } from './components/create-bundle-dialog/create-bundle-dialog.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MessageDetailsComponent } from './components/message-details/message-details.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { BrowserModule } from '@angular/platform-browser';

const YANDEX_API_KEY = 'fb79ab56-36e3-4d30-90f0-fb48b2249b8e';

@NgModule({
  declarations: [
    // This is where all declared components go! (if you add new component,
    // import it here!)
    ControlMessagesComponent,
    HomeComponent,
    EmailConfirmationComponent,
    VehicleDetailsComponent,
    ShoppingCartComponent,
    AdDetailComponent,
    CreateBundleDialogComponent,
    ResetPasswordComponent,
    MessageDetailsComponent,
    InboxComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AngularYandexMapsModule.forRoot(YANDEX_API_KEY),
    MaterialModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    // If you want your component to be usable from outside of this module,
    // you need to export it. This is because this module is imported in
    // AppModule, so everything that is exported here will be imported in AppModule
    CommonModule,
    BrowserModule,
    AngularYandexMapsModule,
    ControlMessagesComponent,
    VehicleDetailsComponent,
    AdDetailComponent
  ]
})
export class SharedModule { }
