import { Component, OnInit, Optional, Inject } from '@angular/core';
import { Validators, FormControl, FormBuilder, FormGroup } from '@angular/forms';
import { ModelType } from '../../shared/models/model-type.model';
import { ModelTypeService } from '../services/vehicle-features/model-type.service';
import { Vehicle } from '../../shared/models/vehicle.model';
import { VehicleService } from '../services/vehicle-features/vehicle.service';
import { BodyTypeService } from '../services/vehicle-features/body-type.service';
import { FuelTypeService } from '../services/vehicle-features/fuel-type.service';
import { BrandTypeService } from '../services/vehicle-features/brand-type.service';
import { GearboxTypeService } from '../services/vehicle-features/gearbox-type.service';
import { BrandType } from '../../shared/models/brand-type.model';
import { BodyType } from '../../shared/models/body-type.model';
import { GearboxType } from '../../shared/models/gearbox-type.model';
import { FuelType } from '../../shared/models/fuel-type.model';
import { ToastrService } from 'ngx-toastr';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { VehicleListComponent } from '../vehicle-list/vehicle-list.component';

class ImageSnippet {
  pending: boolean = false;
  status: string = 'init';

  constructor(public src: string,
    public file: File) { }
}

@Component({
  selector: 'app-new-vehicle',
  templateUrl: './new-vehicle.component.html',
  styleUrls: ['./new-vehicle.component.css']
})
export class NewVehicleComponent implements OnInit {

  isLinear = true;
  bar = false;
  success = false;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  fourthFormGroup: FormGroup;
  modelListAll: ModelType[] = [];
  modelList: ModelType[] = [];
  brandList: BrandType[] = [];
  bodyList: BodyType[] = [];
  fuelList: FuelType[] = [];
  gearboxList: GearboxType[] = [];
  selectedFile: File;
  imageUrls: any[] = [];
  imageNew: any[] = [];
  selectedFiles: File[] = [];
  newVehicle: Vehicle;

  message: string;

  constructor(private formBuilder: FormBuilder, private modelService: ModelTypeService,
    private vehicleService: VehicleService, private brandService: BrandTypeService,
    private bodyService: BodyTypeService, private fuelService: FuelTypeService,
    private gearboxService: GearboxTypeService, private toastr: ToastrService,
    private router: Router, private activatedRoute: ActivatedRoute) { }


  ngOnInit(): void {
      this.firstFormGroup = this.formBuilder.group({
        selectModel: ['', Validators.required],
        selectBrand: ['', Validators.required],
        selectBody: ['', Validators.required],
        selectFuel: ['', Validators.required],
        selectGearbox: ['', Validators.required]
      });
      this.secondFormGroup = this.formBuilder.group({
        mileage: ['', Validators.required]
      });
      this.thirdFormGroup = this.formBuilder.group({
        childSeats: ['', [Validators.max(4), Validators.min(0)]],
        cdw: [false]
      });
      this.fourthFormGroup = this.formBuilder.group({
        file: ['']
      });
      
      this.modelService.getModelTypes().subscribe(
        data => {
          this.modelListAll = data;
          console.log(this.modelListAll)
        }
      );
      this.brandService.getBrandTypes().subscribe(
        data => {
          this.brandList = data;
          console.log('brands', this.brandList)
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

  onNext1Click() {

  }


  localUrl: any[];
  urls = [];

  onSelectFile(event) {
    var files = event.target.files;
    for(let file of files) {
      if(file.type !== 'image/png' && file.type !=='image/jpg' && file.type !=='image/jpeg') {
        this.toastr.error('Valid types are: .png, .jpg and .jpeg', 'Please upload valid file type')
        return;
      }
      if(file.size >= 8000000) { 
        this.toastr.error('File size limit is 8MB', 'File too big')
        return;
      }
    }
    
    if (event.target.files && event.target.files[0]) {
      var filesAmount = event.target.files.length;
      for (let i = 0; i < filesAmount; i++) {
        var reader = new FileReader();
        reader.onload = (event: any) => {
          this.urls.push(event.target.result);
        }

        reader.readAsDataURL(event.target.files[i]);
        this.selectedFile = event.target.files[i];
        this.selectedFiles.push(this.selectedFile);
      }
    }
  }

  save(vehicle: Vehicle) {
    this.vehicleService.create(vehicle).subscribe(
      data => {
        this.toastr.success('You have successfully added a vehicle!', 'Success')
        this.newVehicle = data;
        this.vehicleService.upload(this.selectedFiles, this.newVehicle.id).subscribe(
          data => {
            //after finishing vehicle creation, redirect to table overview
            this.router.navigate(['../overview'], { relativeTo: this.activatedRoute });
          });
      },
      error => {
        this.toastr.error('Error !', 'Error')
        console.log(error)
      }
    );


  }

  doneClick() {

    const modelType = this.firstFormGroup.value.selectModel;
    const mType = this.modelList.find(x => x.id == modelType);
    console.log(mType + 'Izabrani model')

    const brandType = this.firstFormGroup.value.selectBrand;
    const brType = this.brandList.find(x => x.id == brandType);

    const bodyType = this.firstFormGroup.value.selectBody;
    const bType = this.bodyList.find(x => x.id == bodyType);

    const fuelType = this.firstFormGroup.value.selectFuel;
    const fType = this.fuelList.find(x => x.id == fuelType);

    const gearboxType = this.firstFormGroup.value.selectGearbox;
    const gType = this.gearboxList.find(x => x.id == gearboxType);

    const mileage = this.secondFormGroup.value.mileage;

    const childSeats = this.thirdFormGroup.value.childSeats;

    const cdw = this.thirdFormGroup.value.cdw;

    const newVehicle = new Vehicle(mileage, childSeats, cdw, mType, brType, bType, fType, gType);

    this.save(newVehicle);


  }

  brandClicked(brand: BrandType) {
    console.log(brand)
    for (let m of this.modelListAll) {
      if (m.brandId === brand.id) {
        this.modelList.push(m);
      }
    }
  }

}
