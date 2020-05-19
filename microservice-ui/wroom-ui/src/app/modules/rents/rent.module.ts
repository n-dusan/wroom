import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RentRequestsComponent } from './rent-requests/rent-requests.component';
import { RentRoutingModule } from './rent-routing-module';
import { RentsComponent } from './rents.component';
import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../shared/material.module';

//modul za sve /rents/... komponente
@NgModule({
  declarations: [
    RentRequestsComponent,
    RentsComponent
  ],
  imports: [
    RentRoutingModule,
    SharedModule,
    MaterialModule
  ]
})
export class RentModule { }
