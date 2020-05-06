import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ModelType } from '../../modules/shared/models/model-type.model';
import { environment } from '../../../environments/environment'

@Injectable({
    providedIn: 'root'
})
export class ModelTypeService {
  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/model-type';

    constructor(private http: HttpClient){}

    create(modelType: ModelType): Observable<any> {
        return this.http.post(this.baseUrl + "", modelType);
    }

    getModelTypes(): Observable<any> {
         return this.http.get(`${this.baseUrl}` + '');
    }

    delete(name: string): Observable<any>{
        return this.http.delete(`${this.baseUrl}/${name}`);
    }

    update(id: number, value: any){
        return this.http.put(`${this.baseUrl}/${id}`, value);
    }



}
