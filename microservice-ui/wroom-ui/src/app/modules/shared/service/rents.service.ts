import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { RentRequest } from '../models/rent-request.model';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class RentsService {

    private baseUrl: string = environment.protocol 
                        + '://' 
                        + environment.domain 
                        + ':' 
                        + environment.port 
                        + environment.api 
                        + environment.rentingService
                        + '/rents';

    constructor(private httpClient: HttpClient,
        private router: Router) { }

    send(request: RentRequest): Observable<RentRequest> {
        return this.httpClient.post<RentRequest>(this.baseUrl, request);
    }

    sendBundle(bundle: RentRequest[]): Observable<RentRequest> {
        return this.httpClient.post<RentRequest>(this.baseUrl + '/bundle', bundle);
    }

}
