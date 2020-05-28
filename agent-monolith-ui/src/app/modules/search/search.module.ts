import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchRoutingModule } from './search-routing.module';
import { SearchAdsComponent } from './components/search-ads/search-ads.component';
import { SearchComponent } from './search.component';
import { MaterialModule } from '../shared/material.module';
import { AdService } from './service/ad.service';
import { AdDetailComponent } from './components/ad-detail/ad-detail.component';



@NgModule({
  declarations: [
    SearchComponent, 
    SearchAdsComponent, AdDetailComponent
  ],
  imports: [
    CommonModule,
    SearchRoutingModule,
    MaterialModule
  ]
})
export class SearchModule { }
