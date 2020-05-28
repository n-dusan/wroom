import { Component, OnInit, Optional, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { VehicleListComponent } from '../vehicle-list/vehicle-list.component';
import { ModelTypeService } from '../services/vehicle-features/model-type.service';
import { ModelType } from '../../shared/models/model-type.model';
import { BrandTypeService } from '../services/vehicle-features/brand-type.service';
import { BodyTypeService } from '../services/vehicle-features/body-type.service';
import { FuelTypeService } from '../services/vehicle-features/fuel-type.service';
import { GearboxTypeService } from '../services/vehicle-features/gearbox-type.service';
import { GearboxType } from '../../shared/models/gearbox-type.model';
import { FuelType } from '../../shared/models/fuel-type.model';
import { BrandType } from '../../shared/models/brand-type.model';
import { BodyType } from '../../shared/models/body-type.model';

@Component({
  selector: 'app-edit-vehicle',
  templateUrl: './edit-vehicle.component.html',
  styleUrls: ['./edit-vehicle.component.css']
})
export class EditVehicleComponent implements OnInit {
  modelList: ModelType[] = [];
  brandList: BrandType[] = [];
  bodyList: BodyType[] = [];
  fuelList: FuelType[] = [];
  gearboxList: GearboxType[] = [];
  
  constructor(private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<VehicleListComponent>,
    private modelService: ModelTypeService,
    private brandService: BrandTypeService,
    private bodyService: BodyTypeService,
    private fuelService: FuelTypeService,
    private gearboxService: GearboxTypeService,
    //@Optional() is used to prevent error if no data is passed
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any) { }

  local_data = this.data;
  ngOnInit(): void {
    this.vehicleUpdateForm = this.formBuilder.group({
      childSeatsEdit: ['', Validators.required],
      mileageEdit: ['', Validators.required],
      cdw: [],
      selectModel: ['', Validators.required],
      selectBody: ['', Validators.required],
      selectBrand: ['', Validators.required],
      selectFuel: ['', Validators.required],
      selectGearbox: ['', Validators.required]
    })
    this.modelService.getModelTypes().subscribe(
      data => {
        this.modelList = data;
        console.log(this.modelList)
      }
    );
    this.brandService.getBrandTypes().subscribe(
      data => {
        this.brandList = data;
      }
    );
    this.bodyService.getBodyTypes().subscribe(
      data => {
        this.bodyList = data;
      }
    );
    this.fuelService.getFuelTypes().subscribe(
      data => {
        this.fuelList = data;
      }
    );
    this.gearboxService.getGearboxTypes().subscribe(
      data => {
        this.gearboxList = data;
      }
    );
  }

  vehicleUpdateForm = new FormGroup({
    childSeatsEdit: new FormControl({ value: this.local_data.element.childSeats, disabled: false}, Validators.required),
    mileageEdit: new FormControl({ value: this.local_data.element.mileage, disabled: false}, Validators.required),
    selectModel: new FormControl({value: this.local_data.element.modelType.name, disabled: false}, Validators.required),
    selectBody: new FormControl({value: this.local_data.element.bodyType.name, disabled: false}, Validators.required),
    selectBrand: new FormControl({value: this.local_data.element.brandType.name, disabled: false}, Validators.required),
    selectFuel: new FormControl({value: this.local_data.element.fuelType.name, disabled: false}, Validators.required),
    selectGearbox: new FormControl({value: this.local_data.element.gearboxType.name, disabled: false}, Validators.required),
    cdw: new FormControl({value: this.local_data.element.cdw, disabled: false}, Validators.required)
  });

}
