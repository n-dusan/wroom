import { NgModule } from '@angular/core';
import { MaterialModule } from '../shared/material.module';
import { AdsRoutingModule } from './ads-routing.module';
import { PriceListsComponent } from './price-lists/price-lists.component'
import { CommonModule } from '@angular/common';
import { DialogComponent } from './price-lists/dialog/dialog.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { PriceListSelectComponent } from './price-lists/price-list-select/price-list-select.component';
import { CreateAdComponent } from './create-ad/create-ad.component';
import { VehicleListSelectComponent } from './create-ad/vehicle-list-select/vehicle-list-select.component';
import { CreateCityComponent } from './create-city/create-city.component';
import { AdsOverviewComponent } from './ads-overview/ads-overview.component';
import { DetailsDialogComponent } from './ads-overview/details-dialog/details-dialog.component';
import { CommentsOverviewComponent } from './comments/comments-overview/comments-overview.component';
import { CommentDetailsComponent } from './comments/comment-details/comment-details.component';

@NgModule({
  declarations: [
    PriceListsComponent,
    DialogComponent,
    PriceListSelectComponent,
    CreateAdComponent,
    VehicleListSelectComponent,
    CreateCityComponent,
    AdsOverviewComponent,
    DetailsDialogComponent,
    CommentsOverviewComponent,
    CommentDetailsComponent
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
