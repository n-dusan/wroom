import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { AdminWelcomeComponent } from './components/admin-welcome/admin-welcome.component';
import { ManageUsersComponent } from './components/manage-users/manage-users.component';
import { RegisterCompanyComponent } from './components/register-company/register-company.component';

const routes: Routes = [
  { path: '',
    children: [
      { path: '', component: AdminWelcomeComponent },
      { path: 'users', component: ManageUsersComponent },
      { path: 'register', component: RegisterCompanyComponent }
  ]},
  { path: 'vehicles', loadChildren: () => import('../../modules/vehicles/vehicles.module').then(mod => mod.VehiclesModule)},
  { path: 'ads', loadChildren: () => import('../../modules/ads/ads.module').then(mod => mod.AdsModule)}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {}
