import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    // This is where all declared components go! (if you add new component,
    // import it here!)
  ],
  imports: [
    CommonModule
  ],
  exports: [
    // If you want your component to be usable from outside of this module,
    // you need to export it. This is because this module is imported in
    // AppModule, so everything that is exported here will be imported in AppModule
    CommonModule
  ]
})
export class SharedModule { }
