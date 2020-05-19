import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AngularYandexMapsModule } from 'angular8-yandex-maps';
import { ControlMessagesComponent } from './control-messages/control-messages.component';
import { HomeComponent } from './components/home/home.component';

const YANDEX_API_KEY = 'fb79ab56-36e3-4d30-90f0-fb48b2249b8e';

@NgModule({
  declarations: [
    // This is where all declared components go! (if you add new component,
    // import it here!)
    ControlMessagesComponent,
    HomeComponent
  ],
  imports: [
    CommonModule,
    AngularYandexMapsModule.forRoot(YANDEX_API_KEY)
  ],
  exports: [
    // If you want your component to be usable from outside of this module,
    // you need to export it. This is because this module is imported in
    // AppModule, so everything that is exported here will be imported in AppModule
    CommonModule,
    AngularYandexMapsModule,
    ControlMessagesComponent
  ]
})
export class SharedModule { }
