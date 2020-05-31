import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchRoutingModule } from './search-routing.module';
import { SearchAdsComponent } from './components/search-ads/search-ads.component';
import { SearchComponent } from './search.component';
import { MaterialModule } from '../shared/material.module';
import { AdDetailComponent } from './components/ad-detail/ad-detail.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { PriceDetailsComponent } from './components/price-details/price-details.component';
import { SharedModule } from '../shared/shared.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';



@NgModule({
  declarations: [
    SearchComponent, 
    SearchAdsComponent,
    AdDetailComponent, 
    PriceDetailsComponent
  ],
  imports: [
    CommonModule,
    SearchRoutingModule,
    MaterialModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class SearchModule { }
