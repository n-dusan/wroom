import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BodyType } from '../models/body-type.model';

@Injectable({
    providedIn: 'root'
})
export class BodyTypeService {
    private baseUrl = "http://localhost:8080/api/body-type";
    
    constructor(private http: HttpClient){}

    create(bodyType: BodyType): Observable<any> {
        return this.http.post(this.baseUrl + "/add", bodyType);
    }


}