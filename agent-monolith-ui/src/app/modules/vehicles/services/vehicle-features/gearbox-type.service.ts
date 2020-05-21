import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment'
import { GearboxType } from 'src/app/modules/shared/models/gearbox-type.model';

@Injectable({
    providedIn: 'root'
})
export class GearboxTypeService {
  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/gearbox-type';

    constructor(private http: HttpClient){}

    create(gearboxType: GearboxType): Observable<any> {
        return this.http.post(this.baseUrl + "", gearboxType);
    }

    getGearboxTypes(): Observable<any> {
         return this.http.get(this.baseUrl + "/all");
    }

    delete(name: string): Observable<any>{
        return this.http.delete(`${this.baseUrl}/${name}`);
    }

    update(id: number, value: any){
        return this.http.put(`${this.baseUrl}/${id}`, value);
    }



}
