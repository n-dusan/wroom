import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchRoutingModule } from './search-routing.module';
import { SearchAdsComponent } from './components/search-ads/search-ads.component';
import { SearchComponent } from './search.component';
import { MaterialModule } from '../shared/material.module';



@NgModule({
  declarations: [
    SearchComponent, 
    SearchAdsComponent
  ],
  imports: [
    CommonModule,
    SearchRoutingModule,
    MaterialModule
  ]
})
export class SearchModule { }
