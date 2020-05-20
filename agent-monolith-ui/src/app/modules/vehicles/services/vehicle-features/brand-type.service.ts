import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BrandType } from '../../../shared/models/brand-type.model'
import { environment } from '../../../../../environments/environment'

@Injectable({
    providedIn: 'root'
})
export class BrandTypeService {

    private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/brand-type';

    constructor(private http: HttpClient){}

    create(brandType: BrandType): Observable<any> {
        return this.http.post(this.baseUrl + "", brandType);
    }

    getBrandTypes(): Observable<any> {
        return this.http.get(this.baseUrl + "/all");
    }

    delete(name: string): Observable<any>{
        return this.http.delete(`${this.baseUrl}/${name}`);
    }

    update(id: number, value: any){
        return this.http.put(`${this.baseUrl}/${id}`, value);
    }

}
