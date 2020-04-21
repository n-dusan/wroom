import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

//u ovaj modul bacajte sve ostale komponente koje biste preneli u neke module (npr loading spinner)
@NgModule({
  declarations: [

  ],
  imports: [
    CommonModule
  ],
  exports: [
    CommonModule
  ]
})
export class SharedModule { }
