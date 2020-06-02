import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { ShoppingCartItem } from '../models/shopping-cart-item.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ad } from '../models/ad.model';
import { environment } from 'src/environments/environment';
import { AdLocation } from '../models/ad-location.model';
import { User } from '../models/user.model';

@Injectable({
    providedIn: 'root'
})
export class AdService {

    private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/ads';


    constructor(private http: HttpClient) { }

    all(): Observable<Ad[]> {
        return this.http.get<Ad[]>(this.baseUrl);
    }

    get(id: number): Observable<Ad> {
        return this.http.get<Ad>(this.baseUrl + '/' + id);
    }

    getLocation(id: number): Observable<AdLocation> {
        return this.http.get<AdLocation>(this.baseUrl + '/location/' + id);
    }

    getOwner(id: number): Observable<User> {
        return this.http.get<User>(this.baseUrl + '/owner/' + id);
    }
}
