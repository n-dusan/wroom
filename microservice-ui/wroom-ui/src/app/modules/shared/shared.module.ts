import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AngularYandexMapsModule, IConfig } from 'angular8-yandex-maps';
import { ControlMessagesComponent } from './control-messages/control-messages.component';
import { HomeComponent } from './components/home/home.component';
import { MaterialModule } from './material.module';
import { EmailConfirmationComponent } from './components/email-confirmation/email-confirmation.component';

import { VehicleDetailsComponent } from '../vehicles/vehicle-details/vehicle-details.component';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { AdDetailComponent } from '../search/components/ad-detail/ad-detail.component';
import { CreateBundleDialogComponent } from './components/create-bundle-dialog/create-bundle-dialog.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { MessageDetailsComponent } from './components/message-details/message-details.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';

import { NgxChartsModule } from '@swimlane/ngx-charts';

const mapConfig: IConfig = {
  apikey: 'fb79ab56-36e3-4d30-90f0-fb48b2249b8e',
  lang: 'en_US',
};

// const YANDEX_API_KEY = 'fb79ab56-36e3-4d30-90f0-fb48b2249b8e';

@NgModule({
  declarations: [
    ControlMessagesComponent,
    HomeComponent,
    EmailConfirmationComponent,
    VehicleDetailsComponent,
    ShoppingCartComponent,
    AdDetailComponent,
    CreateBundleDialogComponent,
    InboxComponent,
    MessageDetailsComponent,
    ResetPasswordComponent
  ],
  imports: [
    CommonModule,
    AngularYandexMapsModule.forRoot(mapConfig),
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    NgxChartsModule
  ],
  exports: [
    // If you want your component to be usable from outside of this module,
    // you need to export it. This is because this module is imported in
    // AppModule, so everything that is exported here will be imported in AppModule
    CommonModule,
    AngularYandexMapsModule,
    ControlMessagesComponent,
    VehicleDetailsComponent,
    AdDetailComponent,
    NgxChartsModule
  ]
})
export class SharedModule { }
