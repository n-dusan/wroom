import { NgModule } from '@angular/core';
import { MaterialModule } from '../shared/material.module';
import { VehiclesRoutingModule } from './vehicles-routing.module';
import { NewModelTypeComponent } from './new-model-type/new-model-type.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { NewBrandTypeComponent } from './new-brand-type/new-brand-type.component';
import { NewBodyTypeComponent } from './new-body-type/new-body-type.component';
import { NewFuelTypeComponent } from './new-fuel-type/new-fuel-type.component';
import { FeaturesOverviewComponent } from './features-overview/features-overview.component';
import { NewGearboxTypeComponent } from './new-gearbox-type/new-gearbox-type.component';
import { NewVehicleComponent } from './new-vehicle/new-vehicle.component';
import { VehicleListComponent } from './vehicle-list/vehicle-list.component';

import { SharedModule } from '../shared/shared.module';
import { EditVehicleComponent } from './edit-vehicle/edit-vehicle.component';
import { VehicleOccupancyComponent } from './vehicle-occupancy/vehicle-occupancy.component';

@NgModule({
  declarations: [
    NewModelTypeComponent,
    NewBrandTypeComponent,
    NewBodyTypeComponent,
    NewFuelTypeComponent,
    FeaturesOverviewComponent,
    NewGearboxTypeComponent,
    NewVehicleComponent,
    VehicleListComponent,
    EditVehicleComponent,
    VehicleOccupancyComponent
  ],
  imports: [
    SharedModule,
    VehiclesRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule,
    MaterialModule
  ],
  exports: []
})
export class VehiclesModule {}
