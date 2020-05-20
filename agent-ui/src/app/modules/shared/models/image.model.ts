import { Vehicle } from './vehicle.model';

export class Image{
    urlPath:string;
    vehicle:Vehicle;
    constructor( urlPath?: string, vehicle?: Vehicle){
        this.urlPath= urlPath;
        this.vehicle = vehicle;
    }
}