import { NgModule } from '@angular/core';
import { RentsRoutingModule } from './rents-routing.module';
import { MaterialModule } from '../shared/material.module';
import { VehicleOccupancyListComponent } from './components/vehicle-occupancy-list/vehicle-occupancy-list.component';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { ContactOwnerComponent } from './components/contact-owner/contact-owner.component';
import { BundleDialogComponent } from './components/bundle-dialog/bundle-dialog.component';
import { RentReportDialogComponent } from './components/rent-report-dialog/rent-report-dialog.component';
import { RequestsOverviewComponent } from './components/requests-overview/requests-overview.component';
import { StatisticsComponent } from './components/statistics/statistics.component';

@NgModule({
  declarations: [
    VehicleOccupancyListComponent,
    ContactOwnerComponent,
    BundleDialogComponent,
    RentReportDialogComponent,
    RequestsOverviewComponent,
    StatisticsComponent
  ],
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
