import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PriceListsComponent } from './price-lists/price-lists.component';
import { CreateAdComponent } from './create-ad/create-ad.component';


const routes: Routes = [
  { path: '',
    children: [
      { path: 'price-lists', component: PriceListsComponent},
      { path: 'new', component: CreateAdComponent}
  ] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdsRoutingModule {}
