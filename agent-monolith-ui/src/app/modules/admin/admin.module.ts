import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MaterialModule } from '../shared/material.module';
import { SharedModule } from '../shared/shared.module'
import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { AdminWelcomeComponent } from './components/admin-welcome/admin-welcome.component';
import { ManageUsersComponent } from './components/manage-users/manage-users.component';


@NgModule({
  declarations: [
    AdminComponent,
    AdminWelcomeComponent,
    ManageUsersComponent
  ],
  imports: [
    AdminRoutingModule,
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ]
})
export class AdminModule { }
