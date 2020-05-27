import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { BrandType } from '../../../shared/models/brand-type.model'
import { environment } from '../../../../../environments/environment'
import { catchError } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class BrandTypeService {

    private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/brand-type';

    constructor(private http: HttpClient){}

    create(brandType: BrandType): Observable<any> {
        return this.http.post(this.baseUrl + "", brandType).pipe(catchError(this.handleException));
    }

    getBrandTypes(): Observable<any> {
        return this.http.get(this.baseUrl);
    }

    delete(name: string): Observable<any>{
        return this.http.delete(`${this.baseUrl}/${name}`);
    }

    update(id: number, value: any){
        return this.http.put(`${this.baseUrl}/${id}`, value);
    }

    private handleException(err: HttpErrorResponse): Observable<never> {
        return throwError(err.error);
      }

}
