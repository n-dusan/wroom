import { NgModule } from '@angular/core';
import { MaterialModule } from '../shared/material.module';
import { VehiclesRoutingModule } from './vehicles-routing.module';

@NgModule({
  declarations: [],
  imports: [
    MaterialModule,
    VehiclesRoutingModule
  ],
  exports: []
})
export class VehiclesModule {}
