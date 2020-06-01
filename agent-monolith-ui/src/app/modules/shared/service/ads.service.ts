import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { ShoppingCartItem } from '../models/shopping-cart-item.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ad } from '../models/ad.model';
import { environment } from 'src/environments/environment';

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
}