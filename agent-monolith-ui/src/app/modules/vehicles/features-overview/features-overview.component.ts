import { Component, OnInit, Inject } from '@angular/core';
import { ModelType } from 'src/app/modules/shared/models/model-type.model';
import { Observable } from 'rxjs';
import { ModelTypeService } from '../services/vehicle-features/model-type.service';
import { MatTableDataSource } from '@angular/material/table';
import { BodyTypeService } from '../services/vehicle-features/body-type.service';
import { BrandTypeService } from '../services/vehicle-features/brand-type.service';
import { FuelTypeService } from '../services/vehicle-features/fuel-type.service';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NewModelTypeComponent } from '../new-model-type/new-model-type.component';
import { NewBodyTypeComponent } from '../new-body-type/new-body-type.component';
import { NewBrandTypeComponent } from '../new-brand-type/new-brand-type.component';
import { NewFuelTypeComponent } from '../new-fuel-type/new-fuel-type.component';
import { GearboxTypeService } from '../services/vehicle-features/gearbox-type.service';
import { NewGearboxTypeComponent } from '../new-gearbox-type/new-gearbox-type.component';


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
  dataGearboxSource : MatTableDataSource<any>;
  models: Observable<ModelType[]>;
  isAdd: boolean = true;

  constructor(private modelService: ModelTypeService, private bodyService: BodyTypeService,
    private brandService: BrandTypeService, private fuelService: FuelTypeService,
    public dialog: MatDialog, private gearboxService: GearboxTypeService,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.loadModelData();
    this.loadBodyData();
    this.loadBrandData();
    this.loadFuelData();
    this.loadGearboxData();
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
        // console.log('model data', this.dataModelSource)
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

  loadGearboxData(){
    this.gearboxService.getGearboxTypes().subscribe(
      data => {
        this.dataGearboxSource = new MatTableDataSource(data);
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
      this.loadModelData();
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
      this.loadBodyData();
    });
  }

  openBrandDialog(): void {
    const dialogRef = this.dialog.open(NewBrandTypeComponent, {
      width: '500px',
      height: '400px',
      data: {isAdd: this.isAdd=true}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadBrandData();
    });
  }

  openFuelDialog(): void {
    const dialogRef = this.dialog.open(NewFuelTypeComponent, {
      width: '500px',
      height: '400px',
      data: {isAdd: this.isAdd=true}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadFuelData();
    });
  }

  openGearboxDialog(): void {
    const dialogRef = this.dialog.open(NewGearboxTypeComponent, {
      width: '500px',
      height: '400px',
      data: {isAdd: this.isAdd=true}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadGearboxData();
    });
  }

  deleteModel(name: string){
    this.modelService.delete(name).subscribe(
      data => {
        this.loadModelData();
      }
    )
  }

  deleteBody(name: string){
    this.bodyService.delete(name).subscribe(
      data => {
        this.loadBodyData();
      }
    )
  }

  deleteBrand(name: string){
    this.brandService.delete(name).subscribe(
      data => {
        this.loadBrandData();
      }
    )
  }

  deleteFuel(name: string){
    this.fuelService.delete(name).subscribe(
      data => {
        this.loadFuelData();
      }
    )
  }

  deleteGearbox(name: string){
    this.gearboxService.delete(name).subscribe(
      data => {
        this.loadGearboxData();
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
    dialogRef.afterClosed().subscribe(result => {
      this.loadModelData();
    });

  }

  editBody(element: any): void {
    console.log(element.name);
    const dialogRef = this.dialog.open(NewBodyTypeComponent, {
      width: '500px',
      height: '400px',
      data: element
    });
    dialogRef.afterClosed().subscribe(result => {
      this.loadBodyData();
    });

  }

  editBrand(element: any): void {
    console.log(element.name);
    const dialogRef = this.dialog.open(NewBrandTypeComponent, {
      width: '500px',
      height: '400px',
      data: element
    });
    dialogRef.afterClosed().subscribe(result => {
      this.loadBrandData();
    });

  }

  editFuel(element: any): void {
    console.log(element.name);
    const dialogRef = this.dialog.open(NewFuelTypeComponent, {
      width: '500px',
      height: '400px',
      data: element
    });
    dialogRef.afterClosed().subscribe(result => {
      this.loadFuelData();
    });

  }

  editGearbox(element: any): void {
    console.log(element.name);
    const dialogRef = this.dialog.open(NewGearboxTypeComponent, {
      width: '500px',
      height: '400px',
      data: element
    });
    dialogRef.afterClosed().subscribe(result => {
      this.loadGearboxData();
    });
  }

}



