import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';
import { MakingVehiclesFormComponent } from './modules/vehicles/making-vehicles-form/making-vehicles-form.component';

//u app routeru bi na kraju se svelo na importe koji su bas moduli koje imamo u angularu.
//primer: /search, /rents, /messages, /...itd (primetite da ovo odgovara mikroservisima koje bismo imali)
//zasto ovaj pristup? jer smo bez ikakvih djubre routera koje ste imali na isi, kao i svih onih silnih importa u app.modules.ts
//gledao sam kurs folder 22 - Angular Modules & Optimizing Angular Apps

const routes: Routes = [
  { path: 'rents', loadChildren: () => import('./modules/rents/rent.module').then(mod => mod.RentModule)},
  { path: 'neki vas import, razmisljajte o podeli na module!', redirectTo: 'rents', pathMatch: 'full' },
  { path: 'making-vehicles-form', component: MakingVehiclesFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
