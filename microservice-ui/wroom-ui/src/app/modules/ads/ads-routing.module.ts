import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PriceListsComponent } from './price-lists/price-lists.component';
import { CreateAdComponent } from './create-ad/create-ad.component';
import { AdsOverviewComponent } from './ads-overview/ads-overview.component';
import { CreateAdGuard } from './guards/create-ad.guard';
import { CommentsOverviewComponent } from './comments/comments-overview/comments-overview.component';


const routes: Routes = [
  { path: '',
    children: [
      { path: 'price-lists', component: PriceListsComponent},
      { path: 'new', component: CreateAdComponent, canActivate: [CreateAdGuard] },
      { path: 'new/:id', component: CreateAdComponent },
      { path: 'overview', component: AdsOverviewComponent },
      { path: 'comments', component: CommentsOverviewComponent}
  ] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdsRoutingModule {}
