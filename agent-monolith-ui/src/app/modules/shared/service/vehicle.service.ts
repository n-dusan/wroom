import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Vehicle } from '../models/vehicle.model';
import { VehicleFeature } from '../models/vehicle-feature.model';
import { VehicleImage } from '../models/vehicle-image.model';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/vehicle';
  private brandUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/brand-type';
  private modelUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/model-type';
  private fuelUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/fuel-type';
  private gearboxUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/gearbox-type';
  private bodyUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/body-type';


  constructor(private http: HttpClient) { }

  public all() : Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(this.baseUrl + '/all');
  }

  public get(id: number) : Observable<Vehicle> {
    return this.http.get<Vehicle>(this.baseUrl + '/' + id);
  }

  public getBrands() : Observable<VehicleFeature[]> {
    return this.http.get<VehicleFeature[]>(this.brandUrl);
  }

  public getModels() : Observable<VehicleFeature[]> {
    return this.http.get<VehicleFeature[]>(this.modelUrl);
  }

  public getFuels() : Observable<VehicleFeature[]> {
    return this.http.get<VehicleFeature[]>(this.fuelUrl);
  }

  public getGearboxes() : Observable<VehicleFeature[]> {
    return this.http.get<VehicleFeature[]>(this.gearboxUrl);
  }

  public getBodies() : Observable<VehicleFeature[]> {
    return this.http.get<VehicleFeature[]>(this.bodyUrl);
  }

  // Gets an array of objects like {vehicleId: n, image: base64img} for all vehicles
  public getVehicleImage() : Observable<VehicleImage[]> {
    return this.http.get<VehicleImage[]>(this.baseUrl + '/with-image');
  }

  // Gets an array of objects like {image: base64img} for one vehicle
  public getVehicleImages(vehicleId: number) : Observable<string[]> {
    return this.http.get<string[]>(this.baseUrl + '/getImages/' + vehicleId);
  }

  allVehicles(): Observable<Vehicle[]>{
    return this.http.get<Vehicle[]>(this.baseUrl + '/allVehicles');  
  }

  // public getOneAdImage(ad_id: number) : Observable<string> {
  //   return this.http.get<string>(this.baseUrl + '/image/ad/' + ad_id);
  // }
}