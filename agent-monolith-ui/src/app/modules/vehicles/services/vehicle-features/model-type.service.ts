import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment } from '../../../../../environments/environment'
import { ModelType } from 'src/app/modules/shared/models/model-type.model';
import { catchError } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class ModelTypeService {
  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/model-type';

    constructor(private http: HttpClient){}

    create(modelType: ModelType): Observable<any> {
        return this.http.post(this.baseUrl + "", modelType).pipe(catchError(this.handleException));
    }

    getModelTypes(): Observable<any> {
         return this.http.get(this.baseUrl);
    }

    delete(id: number): Observable<any>{
        return this.http.delete(`${this.baseUrl}/${id}`);
    }

    update(id: number, value: any){
        return this.http.put(`${this.baseUrl}/${id}`, value);
    }

    private handleException(err: HttpErrorResponse): Observable<never> {
        return throwError(err.error);
      }

}
