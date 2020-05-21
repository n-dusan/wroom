import { ModelType } from './model-type.model';
import { BodyType } from './body-type.model';
import { BrandType } from './brand-type.model';
import { FuelType } from './fuel-type.model';
import { GearboxType } from './gearbox-type.model';
import { Image } from './image.model';

export class Vehicle{
    id:number;
    mileage:number;
    childSeats:number;
    cdw:boolean;
    modelType:ModelType;
    bodyType:BodyType;
    brandType:BrandType;
    fuelType:FuelType;
    gearboxType:GearboxType;

    constructor( mileage?: number, childSeats?: number, cdw?: boolean, modelType?: ModelType, brandType?: BrandType, bodyType?: BodyType,  fuelType?: FuelType,
        gearboxType?: GearboxType){
      
        this.mileage = mileage;
        this.childSeats = childSeats;
        this.cdw = cdw;
        this.modelType = modelType;
        this.bodyType = bodyType;
        this.brandType = brandType;
        this.fuelType = fuelType;
        this.gearboxType = gearboxType;
    }
}