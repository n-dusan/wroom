import { NgModule } from '@angular/core';
import { RentsRoutingModule } from './rents-routing.module';
import { MaterialModule } from '../shared/material.module';
import { VehicleOccupancyListComponent } from './components/vehicle-occupancy-list/vehicle-occupancy-list.component';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { RequestsOverviewComponent } from './components/requests-overview/requests-overview.component';
import { ContactOwnerComponent } from './components/contact-owner/contact-owner.component';

@NgModule({
  declarations: [VehicleOccupancyListComponent, RequestsOverviewComponent, ContactOwnerComponent],
  imports: [
    RentsRoutingModule,
    MaterialModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ],
  exports: []
})
export class RentsModule {}
