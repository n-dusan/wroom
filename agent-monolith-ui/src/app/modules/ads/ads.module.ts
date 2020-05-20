import { NgModule } from '@angular/core';
import { MaterialModule } from '../shared/material.module';
import { AdsRoutingModule } from './ads-routing.module';
import { PriceListsComponent } from './price-lists/price-lists.component'
import { CommonModule } from '@angular/common';
import { DialogComponent } from './price-lists/dialog/dialog.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { PriceListSelectComponent } from './price-lists/price-list-select/price-list-select.component';

@NgModule({
  declarations: [
    PriceListsComponent,
    DialogComponent,
    PriceListSelectComponent
  ],
  imports: [
    MaterialModule,
    AdsRoutingModule,
    CommonModule,
    ReactiveFormsModule,
    SharedModule,
    FormsModule
  ],
  exports: []
})
export class AdsModule {}
