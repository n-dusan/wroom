import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  { path: '',
    //component: AAAAAAA,
    children: [
      //{ path: 'aaaaaaaaaa', component: AAAAA}
  ] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdsRoutingModule {}
