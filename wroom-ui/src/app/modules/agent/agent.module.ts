import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AgentRoutingModule } from './agent-routing.module';
import { HomeAgentComponent } from './home-agent/home-agent.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [
    HomeAgentComponent
  ],
  imports: [
    AgentRoutingModule, 
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
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
