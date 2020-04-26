import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NewModelTypeComponent } from './new-model-type/new-model-type.component';


const routes: Routes = [
  { path: '',
    //component: AAAAAAA,
    component: NewModelTypeComponent,
    children: [
      { path: 'new-model-type', component: NewModelTypeComponent}
  ] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VehiclesRoutingModule {}
