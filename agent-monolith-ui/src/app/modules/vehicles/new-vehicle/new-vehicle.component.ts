import { Component, OnInit } from '@angular/core';
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

class ImageSnippet {
  pending: boolean = false;
  status: string = 'init';
  constructor(public src: string, public file: File) {}
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
  modelList: ModelType[] = [];
  brandList: BrandType[] = [];
  bodyList: BodyType[] = [];
  fuelList: FuelType[] = [];
  gearboxList: GearboxType[] = [];
  selectedFile: File;
  imageUrls: any[] = [];
  imageNew: any[] = [];
  selectedFiles: File[]=[];
  newVehicle: Vehicle;
  
  message: string;

  constructor(private formBuilder: FormBuilder, private modelService: ModelTypeService, 
    private vehicleService: VehicleService, private brandService: BrandTypeService,
    private bodyService: BodyTypeService, private fuelService: FuelTypeService,
    private gearboxService: GearboxTypeService, private toastr: ToastrService) { }

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
        childSeats: ['', [Validators.max(5), Validators.min(3)]],
        cdw: [false]
      });
      this.fourthFormGroup = this.formBuilder.group({
        file: ['']
      });
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
 
  onNext1Click(){

  }

  
  localUrl: any[];
  urls = [];
  
  onSelectFile(event) {
    if (event.target.files && event.target.files[0]) {
        var filesAmount = event.target.files.length;
        for (let i = 0; i < filesAmount; i++) {
                var reader = new FileReader();
                reader.onload = (event:any) => {
                   this.urls.push(event.target.result); 
                }
                
                reader.readAsDataURL(event.target.files[i]);
                this.selectedFile = event.target.files[i];
                this.selectedFiles.push(this.selectedFile);
        }
    } 
  }

  save(vehicle: Vehicle){
    
    this.vehicleService.create(vehicle).subscribe(
      data => {
        this.toastr.success('You have successfully added a vehicle!', 'Success')
        this.newVehicle = data;
        this.vehicleService.upload(this.selectedFiles, this.newVehicle.id).subscribe(
          data => {
            console.log('Uspesno!')
          });
      },
      error=> {
      this.toastr.error('Error !', 'Error')
      console.log('ERROR MY BOYO', error)
      }
    );
    

  }

  doneClick(){
    
        const modelType = this.firstFormGroup.value.selectModel;
        const mType = this.modelList.find(x => x.id == modelType);

        const brandType = this.firstFormGroup.value.selectBrand;
        const brType = this.brandList.find(x => x.id == brandType);

        const bodyType = this.firstFormGroup.value.selectBody;
        const bType = this.bodyList.find(x => x.id == bodyType);

        const fuelType = this.firstFormGroup.value.selectFuel;
        const fType = this.bodyList.find(x => x.id == fuelType);

        const gearboxType = this.firstFormGroup.value.selectGearbox;
        const gType = this.bodyList.find(x => x.id == gearboxType);
      
        const mileage = this.secondFormGroup.value.mileage;

        const childSeats = this.thirdFormGroup.value.childSeats;

        const cdw = this.thirdFormGroup.value.cdw;
        
        const newVehicle = new Vehicle(mileage, childSeats, cdw, mType,brType,bType,fType,gType);
        
        this.save(newVehicle); 
      
    
  }

  
  

}
