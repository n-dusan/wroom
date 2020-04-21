import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { FlexLayoutModule } from '@angular/flex-layout';


//dodajite sve te silne module u ovaj array i to je to
const modules = [
  MatButtonModule,
  MatTableModule,
  FlexLayoutModule
]

@NgModule({
  imports: [...modules],
  exports: modules
})
export class MaterialModule {}
