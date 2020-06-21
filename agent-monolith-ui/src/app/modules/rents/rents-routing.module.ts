import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VehicleOccupancyListComponent } from './components/vehicle-occupancy-list/vehicle-occupancy-list.component';
import { RequestsOverviewComponent } from './components/requests-overview/requests-overview.component';
import { StatisticsComponent } from './components/statistics/statistics.component';


const routes: Routes = [
  { path: '',
    children: [
      { path: 'overview', component: VehicleOccupancyListComponent },
      { path: 'requests', component: RequestsOverviewComponent },
      { path: 'statistics', component: StatisticsComponent }
  ] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RentsRoutingModule {}
