import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NewModelTypeComponent } from './new-model-type/new-model-type.component';
import { NewBrandTypeComponent } from './new-brand-type/new-brand-type.component';
import { NewBodyTypeComponent } from './new-body-type/new-body-type.component';


const routes: Routes = [
  { path: '',
    //component: AAAAAAA,
    children: [
      { path: 'new-model-type', component: NewModelTypeComponent},
      { path: 'new-brand-type', component: NewBrandTypeComponent },
      { path: 'new-body-type', component: NewBodyTypeComponent }
  ] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VehiclesRoutingModule {}
