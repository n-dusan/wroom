import { Component, OnInit, Inject, Input } from '@angular/core';
import { ModelType } from 'src/app/models/model-type.model';
import { Observable } from 'rxjs';
import { ModelTypeService } from 'src/app/services/model-type.service';
import { MatTableDataSource } from '@angular/material/table';
import { BodyTypeService } from 'src/app/services/body-type.service';
import { BrandTypeService } from 'src/app/services/brand-type.service';
import { FuelTypeService } from 'src/app/services/fuel-type.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NewModelTypeComponent } from '../new-model-type/new-model-type.component';
import { NewBodyTypeComponent } from '../new-body-type/new-body-type.component';
import { NewBrandTypeComponent } from '../new-brand-type/new-brand-type.component';
import { NewFuelTypeComponent } from '../new-fuel-type/new-fuel-type.component';


@Component({
  selector: 'app-features-overview',
  templateUrl: './features-overview.component.html',
  styleUrls: ['./features-overview.component.css']
})
export class FeaturesOverviewComponent implements OnInit {
  step = 0;
  displayedColumns: string[] = ['id', 'name', 'delete', 'edit'];
  dataModelSource : MatTableDataSource<any>;
  dataBodySource : MatTableDataSource<any>;
  dataBrandSource : MatTableDataSource<any>;
  dataFuelSource : MatTableDataSource<any>;
  models: Observable<ModelType[]>;
  isAdd: boolean = true;

  constructor(private modelService: ModelTypeService, private bodyService: BodyTypeService,
    private brandService: BrandTypeService, private fuelService: FuelTypeService, public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.loadModelData();
    this.loadBodyData();
    this.loadBrandData();
    this.loadFuelData();
  }

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  loadModelData(){
    this.modelService.getModelTypes().subscribe(
      data => {
        this.dataModelSource = new MatTableDataSource(data);
        
      });
  }

  loadBodyData(){
    this.bodyService.getBodyTypes().subscribe(
      data => {
        this.dataBodySource = new MatTableDataSource(data);
      }
    )
  }

  loadBrandData(){
    this.brandService.getBrandTypes().subscribe(
      data => {
        this.dataBrandSource = new MatTableDataSource(data);
      }
    )
  }

  loadFuelData(){
    this.fuelService.getFuelTypes().subscribe(
      data => {
        this.dataFuelSource = new MatTableDataSource(data);
      }
    )
  }

  openModelDialog(): void {
    const dialogRef = this.dialog.open(NewModelTypeComponent, {
      width: '500px',
      height: '400px',
      data: {isAdd: this.isAdd=true }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
    console.log(this.isAdd)
  }

  openBodyDialog(): void {
    const dialogRef = this.dialog.open(NewBodyTypeComponent, {
      width: '500px',
      height: '400px',
      data: {isAdd: this.isAdd=true}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openBrandDialog(): void {
    const dialogRef = this.dialog.open(NewBrandTypeComponent, {
      width: '500px',
      height: '400px',
      data: {isAdd: this.isAdd=true}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openFuelDialog(): void {
    const dialogRef = this.dialog.open(NewFuelTypeComponent, {
      width: '500px',
      height: '400px',
      data: {isAdd: this.isAdd=true}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  deleteModel(name: string){
    this.modelService.delete(name).subscribe(
      data => {
        console.log('Deleted');
        window.location.reload();
      }
    )
  }

  deleteBody(name: string){
    this.bodyService.delete(name).subscribe(
      data => {
        console.log('Deleted');
        window.location.reload();
      }
    )
  }

  deleteBrand(name: string){
    this.brandService.delete(name).subscribe(
      data => {
        console.log('Deleted');
        window.location.reload();
      }
    )
  }

  deleteFuel(name: string){
    this.fuelService.delete(name).subscribe(
      data => {
        console.log('Deleted');
        window.location.reload();
      }
    )
  }

  editModel(element: any): void {
    console.log(element.name);
    const dialogRef = this.dialog.open(NewModelTypeComponent, {
      width: '500px',
      height: '400px',
      data: element
      
    });
    
  }

  editBody(element: any): void {
    console.log(element.name);
    const dialogRef = this.dialog.open(NewBodyTypeComponent, {
      width: '500px',
      height: '400px',
      data: element
      
    });
    
  }

  editBrand(element: any): void {
    console.log(element.name);
    const dialogRef = this.dialog.open(NewBrandTypeComponent, {
      width: '500px',
      height: '400px',
      data: element
      
    });
    
  }

  editFuel(element: any): void {
    console.log(element.name);
    const dialogRef = this.dialog.open(NewFuelTypeComponent, {
      width: '500px',
      height: '400px',
      data: element
      
    });
    
  }

}



