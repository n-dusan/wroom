import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment'
import { Vehicle } from 'src/app/modules/shared/models/vehicle.model';

@Injectable({
    providedIn: 'root'
})
export class VehicleService {
  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/vehicle';

    constructor(private http: HttpClient){}

    create(vehicle: Vehicle): Observable<any> {
        return this.http.post(this.baseUrl, vehicle);
    }

    upload(file: File[], id: number): Observable<any>{
        let formData = new FormData();
            for(let i = 0; i < file.length;i++){
            let formD : FormData;
            formData.append('file', file[i]);
            
            }
    
        return this.http.post(`${this.baseUrl}/upload/` + id, formData);
    }
}
