import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './modules/shared/components/home/home.component';
import { AlreadyAuthenticatedGuard } from './modules/auth/guards/alreadyAuthenticated.guard';
import { EmailConfirmationComponent } from './modules/shared/components/email-confirmation/email-confirmation.component';
import { ShoppingCartComponent } from './modules/shared/components/shopping-cart/shopping-cart.component';
import { AdminGuard } from './modules/shared/guards/admin.guard';
import { ResetPasswordComponent } from './modules/shared/components/reset-password/reset-password.component';
import { InboxComponent } from './modules/shared/components/inbox/inbox.component';
import { DebtsComponent } from './modules/shared/components/debts/debts.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'cart', component: ShoppingCartComponent },
  { path: 'debts', component: DebtsComponent },
  { path: 'inbox', component: InboxComponent },
  { path: 'confirm/:token', component: EmailConfirmationComponent },
  { path: 'reset-password/:token', component: ResetPasswordComponent },
  { path: 'auth', loadChildren: () => import('./modules/auth/auth.module').then(mod => mod.AuthModule)/*, canActivate:[AlreadyAuthenticatedGuard]*/},
  { path: 'rents', loadChildren: () => import('./modules/rents/rents.module').then(mod => mod.RentsModule)},
  { path: 'ads', loadChildren: () => import('./modules/ads/ads.module').then(mod => mod.AdsModule)},
  { path: 'vehicles', loadChildren: () => import('./modules/vehicles/vehicles.module').then(mod => mod.VehiclesModule)},
  { path: 'search', loadChildren: () => import('./modules/search/search.module').then(mod => mod.SearchModule)},
  { path: 'dashboard', loadChildren: () => import('./modules/admin/admin.module').then(mod => mod.AdminModule), canActivate:[AdminGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
