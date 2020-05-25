import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './modules/shared/components/home/home.component';
import { AlreadyAuthenticatedGuard } from './modules/auth/guards/alreadyAuthenticated.guard';
import { AuthGuard } from './modules/auth/guards/auth.guard';


const routes: Routes = [
  { path: '', redirectTo: '/auth', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate:[AuthGuard] },
  { path: 'auth', loadChildren: () => import('./modules/auth/auth.module').then(mod => mod.AuthModule) /*, canActivate:[AlreadyAuthenticatedGuard]*/},
  { path: 'rents', loadChildren: () => import('./modules/rents/rents.module').then(mod => mod.RentsModule)},
  { path: 'ads', loadChildren: () => import('./modules/ads/ads.module').then(mod => mod.AdsModule)},
  { path: 'vehicles', loadChildren: () => import('./modules/vehicles/vehicles.module').then(mod => mod.VehiclesModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
