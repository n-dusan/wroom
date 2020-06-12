import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RequestsOverviewComponent } from './components/requests-overview/requests-overview.component';
import { VehicleOccupancyListComponent } from './components/vehicle-occupancy-list/vehicle-occupancy-list.component';


const routes: Routes = [
  { path: '',
    // component: VehicleOccupancyListComponent,
    children: [
      {path: 'overview', component: VehicleOccupancyListComponent},
      {path: 'requests', component: RequestsOverviewComponent}
  ] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RentsRoutingModule {}
