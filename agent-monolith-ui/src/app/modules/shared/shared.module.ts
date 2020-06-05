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
    CreateBundleDialogComponent
  ],
  imports: [
    CommonModule,
    AngularYandexMapsModule.forRoot(YANDEX_API_KEY),
    MaterialModule
  ],
  exports: [
    // If you want your component to be usable from outside of this module,
    // you need to export it. This is because this module is imported in
    // AppModule, so everything that is exported here will be imported in AppModule
    CommonModule,
    AngularYandexMapsModule,
    ControlMessagesComponent,
    VehicleDetailsComponent,
    AdDetailComponent
  ]
})
export class SharedModule { }
