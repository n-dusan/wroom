import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ad } from '../models/ad.model';
import { AdLocation } from '../models/ad-location.model';
import { Vehicle } from '../models/vehicle.model';
import { VehicleImage } from '../models/vehicle-image.model';
import { PriceList } from '../models/price-list.model';
import { VehicleFeature } from '../models/vehicle-feature.model';
import { User } from '../models/user.model';

@Injectable({
    providedIn: 'root'
})
export class SearchService {

    private baseUrl = environment.protocol
        + '://'
        + environment.domain
        + ':'
        + environment.port
        + environment.api
        + environment.searchService;

    constructor(private http: HttpClient) { }

    public ads(): Observable<Ad[]> {
        return this.http.get<Ad[]>(this.baseUrl + '/search/ads');
    }

    public getAd(id: number): Observable<Ad> {
        return this.http.get<Ad>(this.baseUrl + '/search/ads/' + id);
    }

    public getOwner(id: number): Observable<User> {
        return this.http.get<User>(this.baseUrl + '/search/ads/owner/' + id);
    }

    public getLocations(): Observable<AdLocation[]> {
        return this.http.get<AdLocation[]>(this.baseUrl + '/search/locations');
    }

    public getLocation(id: number): Observable<AdLocation> {
        return this.http.get<AdLocation>(this.baseUrl + '/search/locations/' + id);
    }

    public vehicles(): Observable<Vehicle[]> {
        return this.http.get<Vehicle[]>(this.baseUrl + '/search/vehicles');
    }

    public getVehicle(id: number): Observable<Vehicle> {
        return this.http.get<Vehicle>(this.baseUrl + '/search/vehicles/' + id);
    }

    // Gets an array of objects like {vehicleId: n, image: base64img} for all vehicles
    public getVehicleImage(): Observable<VehicleImage[]> {
        return this.http.get<VehicleImage[]>(this.baseUrl + '/search/vehicles/with-image');
    }

    // Gets an array of objects like {image: base64img} for one vehicle
    public getVehicleImages(vehicleId: number): Observable<string[]> {
        return this.http.get<string[]>(this.baseUrl + '/search/vehicles/images/' + vehicleId);
    }

    public pricelists(): Observable<PriceList[]> {
        return this.http.get<PriceList[]>(this.baseUrl + '/search/pricelists');
    }

    public getPricelist(id: number): Observable<PriceList> {
        return this.http.get<PriceList>(this.baseUrl + '/search/pricelists/' + id);
    }

    public getBrands(): Observable<VehicleFeature[]> {
        return this.http.get<VehicleFeature[]>(this.baseUrl + '/search/brands');
    }

    public getModels(): Observable<VehicleFeature[]> {
        return this.http.get<VehicleFeature[]>(this.baseUrl + '/search/models');
    }

    public getFuels(): Observable<VehicleFeature[]> {
        return this.http.get<VehicleFeature[]>(this.baseUrl + '/search/fuels');
    }

    public getGearboxes(): Observable<VehicleFeature[]> {
        return this.http.get<VehicleFeature[]>(this.baseUrl + '/search/gearboxes');
    }

    public getBodies(): Observable<VehicleFeature[]> {
        return this.http.get<VehicleFeature[]>(this.baseUrl + '/search/bodies');
    }

}