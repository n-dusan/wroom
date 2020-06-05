import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VehicleOccupancyListComponent } from './vehicle-occupancy-list/vehicle-occupancy-list.component';


const routes: Routes = [
  { path: '',
    //component: AAAAAAA,
    children: [
       {path: 'occupancy-list', component: VehicleOccupancyListComponent}
  ] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RentsRoutingModule {}
