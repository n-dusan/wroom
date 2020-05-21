import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BodyType } from '../../../shared/models/body-type.model';
import { environment } from '../../../../../environments/environment'
@Injectable({
    providedIn: 'root'
})
export class BodyTypeService {
    private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/body-type';

    constructor(private http: HttpClient){}

    create(bodyType: BodyType): Observable<any> {
        return this.http.post(this.baseUrl + "", bodyType);
    }

    getBodyTypes(): Observable<any> {
        return this.http.get(this.baseUrl + "/all");
    }

    delete(name: string): Observable<any>{
        return this.http.delete(`${this.baseUrl}/${name}`);
    }

    update(id: number, value: any){
        return this.http.put(`${this.baseUrl}/${id}`, value);
    }

}
