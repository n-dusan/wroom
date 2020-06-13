import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RentRequest } from '../../shared/models/rent-request.model';
import { catchError } from 'rxjs/operators';


@Injectable({
    providedIn: 'root'
})
export class RentsService {
  private baseUrl = environment.protocol
            + '://' + environment.domain
            + ':'
            + environment.port
            + environment.api
            + environment.rentingService
            + '/rents';

    constructor(private http: HttpClient){}

    occupy(rentRequest: RentRequest): Observable<any> {
        return this.http.post(this.baseUrl + '/occupy', rentRequest).pipe(catchError(this.handleException));
    }

    getAllActiveForUser(userId: number): Observable<RentRequest[]> {
      return this.http.get<RentRequest[]>(this.baseUrl + '/all/' + userId).pipe(catchError(this.handleException));
    }

    getRequestedForUser(userId:number): Observable<RentRequest[]> {
      return this.http.get<RentRequest[]>(this.baseUrl + '/requested/' + userId).pipe(catchError(this.handleException));
    }

    get(id: number) : Observable<RentRequest> {
      return this.http.get<RentRequest>(this.baseUrl + '/' + id).pipe(catchError(this.handleException));
    }

    decline(id: number): Observable<RentRequest> {
      return this.http.put<RentRequest>(this.baseUrl + '/decline/' + id, {}).pipe(catchError(this.handleException));
    }

    accept(id: number): Observable<RentRequest> {
      return this.http.put<RentRequest>(this.baseUrl + '/accept/' + id, {}).pipe(catchError(this.handleException));
    }

    getPendingForUser(userId: number): Observable<RentRequest[]> {
      return this.http.get<RentRequest[]>(this.baseUrl + '/pending/' + userId).pipe(catchError(this.handleException));
    }

    private handleException(err: HttpErrorResponse): Observable<never> {
      return throwError(err.error);
    }

}
