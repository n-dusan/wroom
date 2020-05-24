import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment } from '../../../../../environments/environment'
import { GearboxType } from 'src/app/modules/shared/models/gearbox-type.model';
import { catchError } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class GearboxTypeService {
  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/gearbox-type';

    constructor(private http: HttpClient){}

    create(gearboxType: GearboxType): Observable<any> {
        return this.http.post(this.baseUrl + "", gearboxType).pipe(catchError(this.handleException));
    }

    getGearboxTypes(): Observable<any> {
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
