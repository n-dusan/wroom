import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BrandType } from '../models/brand-type.model';

@Injectable({
    providedIn: 'root'
})
export class BrandTypeService {
    private baseUrl = "http://localhost:8080/api/brand-type";
    
    constructor(private http: HttpClient){}

    create(brandType: BrandType): Observable<any> {
        return this.http.post(this.baseUrl + "", brandType);
    }

    getBrandTypes(): Observable<any> {
        return this.http.get(`${this.baseUrl}` + '');
    }

    delete(name: string): Observable<any>{
        return this.http.delete(`${this.baseUrl}/${name}`);
    }
    
    update(id: number, value: any){
        return this.http.put(`${this.baseUrl}/${id}`, value);
    }

}