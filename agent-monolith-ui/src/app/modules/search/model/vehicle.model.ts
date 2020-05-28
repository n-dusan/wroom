import { VehicleFeature } from './vehicle-feature.model';

export class Vehicle {

    public constructor(
        public id?: number,
        public mileage?: number,
        public childSeats?: number,
        public cdw?: boolean,
        public modelType?: VehicleFeature,
        public brandType?: VehicleFeature,
        public bodyType?: VehicleFeature,
        public fuelType?: VehicleFeature,
        public gearboxType?: VehicleFeature
    ) {}

}