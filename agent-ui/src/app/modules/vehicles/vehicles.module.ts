import { NgModule } from '@angular/core';
import { MaterialModule } from '../shared/material.module';
import { VehiclesRoutingModule } from './vehicles-routing.module';
import { NewModelTypeComponent } from './new-model-type/new-model-type.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { NewBrandTypeComponent } from './new-brand-type/new-brand-type.component';
import { NewBodyTypeComponent } from './new-body-type/new-body-type.component';

@NgModule({
  declarations: [NewModelTypeComponent, NewBrandTypeComponent, NewBodyTypeComponent],
  imports: [
    MaterialModule,
    VehiclesRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule
  ],
  exports: []
})
export class VehiclesModule {}
