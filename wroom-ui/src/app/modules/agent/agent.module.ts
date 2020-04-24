import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AgentRoutingModule } from './agent-routing.module';
import { HomeAgentComponent } from './home-agent/home-agent.component';
import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../shared/material.module';



@NgModule({
  declarations: [
    HomeAgentComponent
  ],
  imports: [
    AgentRoutingModule, 
    SharedModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
  ]
})
export class AgentModule { }
