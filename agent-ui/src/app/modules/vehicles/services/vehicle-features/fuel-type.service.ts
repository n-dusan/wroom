import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FuelType } from '../../../shared/models/fuel-type.model';
import { environment } from '../../../../../environments/environment'

@Injectable({
    providedIn: 'root'
})
export class FuelTypeService {
  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/fuel-type';

    constructor(private http: HttpClient){}

    create(fuelType: FuelType): Observable<any> {
        return this.http.post(this.baseUrl + "", fuelType);
    }

    getFuelTypes(): Observable<any> {
        return this.http.get(this.baseUrl + "/all");
    }

    delete(name: string): Observable<any>{
        return this.http.delete(`${this.baseUrl}/${name}`);
    }

    update(id: number, value: any){
        return this.http.put(`${this.baseUrl}/${id}`, value);
    }
}
