import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AgentRoutingModule } from './agent-routing.module';
import { HomeAgentComponent } from './home-agent/home-agent.component';
import { SharedModule } from '../shared/shared.module';
import { MakingVehiclesFormComponent } from '../vehicles/making-vehicles-form/making-vehicles-form.component';
import { MaterialModule } from '../shared/material.module';
import { AgentComponent } from './agent.component';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [
    HomeAgentComponent,
    AgentComponent,
    MakingVehiclesFormComponent
  ],
  imports: [
    AgentRoutingModule, 
    MaterialModule,
    SharedModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  exports: [
  ]
})
export class AgentModule { }
