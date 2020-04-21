import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RentRequestsComponent } from './rent-requests/rent-requests.component';
import { RentsComponent } from './rents.component';


const routes: Routes = [
  { path: '',
    component: RentsComponent,
    children: [
      { path: 'requests', component: RentRequestsComponent }
  ] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RentRoutingModule {}
