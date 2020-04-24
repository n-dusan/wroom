import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AgentRoutingModule } from './agent-routing.module';
import { HomeAgentComponent } from './home-agent/home-agent.component';
import { SharedModule } from '../shared/shared.module';
import { MakingVehiclesFormComponent } from '../vehicles/making-vehicles-form/making-vehicles-form.component';
import { MaterialModule } from '../shared/material.module';
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';



@NgModule({
  declarations: [
    MakingVehiclesFormComponent,
    HomeAgentComponent
    
  ],
  imports: [
    AgentRoutingModule, 
    MaterialModule,
    SharedModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    FlexLayoutModule,
    MatButtonModule,
    MatSidenavModule,
    MatToolbarModule,
    MatFormFieldModule
  ],
  exports: [
  ]
})
export class AgentModule { }
