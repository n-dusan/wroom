import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FuelType } from '../models/fuel-type.model';

@Injectable({
    providedIn: 'root'
})
export class FuelTypeService {
    private baseUrl = "http://localhost:8080/api/fuel-type";
    
    constructor(private http: HttpClient){}

    create(fuelType: FuelType): Observable<any> {
        return this.http.post(this.baseUrl + "/add", fuelType);
    }


}