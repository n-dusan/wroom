import { NgModule } from '@angular/core';
import { MaterialModule } from '../shared/material.module';
import { AdsRoutingModule } from './ads-routing.module'

@NgModule({
  declarations: [],
  imports: [
    MaterialModule,
    AdsRoutingModule
  ],
  exports: []
})
export class AdsModule {}
