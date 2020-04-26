import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ModelType } from '../models/model-type.model';

@Injectable({
    providedIn: 'root'
})
export class ModelTypeService {
    private baseUrl = "http://localhost:8080/api/model-type";
    
    constructor(private http: HttpClient){}

    create(modelType: ModelType): Observable<any> {
        return this.http.post(this.baseUrl + "/add", modelType);
    }


}