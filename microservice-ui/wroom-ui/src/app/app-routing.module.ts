import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';
import { MakingVehiclesFormComponent } from './modules/vehicles/making-vehicles-form/making-vehicles-form.component';

const routes: Routes = [
  { path: '', redirectTo: '/agent', pathMatch: 'full' },
  { path: 'agent', loadChildren: () => import('./modules/agent/agent.module').then(mod => mod.AgentModule)},
  { path: 'rents', loadChildren: () => import('./modules/rents/rent.module').then(mod => mod.RentModule)},
  { path: 'neki vas import, razmisljajte o podeli na module!', redirectTo: 'rents', pathMatch: 'full' },
  // { path: 'making-vehicles-form', redirectTo: 'agent', pathMatch: 'full' }
  // { path: 'making-vehicles-form', component: MakingVehiclesFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules, useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
