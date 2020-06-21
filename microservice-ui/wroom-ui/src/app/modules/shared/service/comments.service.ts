import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Comment } from '../models/comment.model';

@Injectable({
    providedIn: 'root'
})
export class CommentsService {

    private baseUrl = environment.protocol
    + '://'
    + environment.domain
    + ':'
    + environment.port
    + environment.api
    + environment.adsService
    + '/comments';


    constructor(private http: HttpClient) { }

    getAll(adId: number): Observable<Comment[]> {
        return this.http.get<Comment[]>(this.baseUrl + '/' + adId);
    }

    getForVehicle(vehicleId: number): Observable<Comment[]> {
      return this.http.get<Comment[]>(this.baseUrl + '/vehicle/' + vehicleId);
    }

    getAvgVehicle(vehicleId: number): Observable<number> {
      return this.http.get<number>(this.baseUrl + '/vehicle/' + vehicleId + '/avg');
    }


}
