import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './modules/shared/components/home/home.component';
import { AlreadyAuthenticatedGuard } from './modules/auth/guards/alreadyAuthenticated.guard';
import { AuthGuard } from './modules/auth/guards/auth.guard';
import { EmailConfirmationComponent } from './modules/shared/components/email-confirmation/email-confirmation.component';


const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'confirm/:token', component: EmailConfirmationComponent }, 
  { path: 'auth', loadChildren: () => import('./modules/auth/auth.module').then(mod => mod.AuthModule), canActivate:[AlreadyAuthenticatedGuard]},
  { path: 'rents', loadChildren: () => import('./modules/rents/rents.module').then(mod => mod.RentsModule)},
  { path: 'ads', loadChildren: () => import('./modules/ads/ads.module').then(mod => mod.AdsModule)},
  { path: 'vehicles', loadChildren: () => import('./modules/vehicles/vehicles.module').then(mod => mod.VehiclesModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
