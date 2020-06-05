import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ad } from '../model/ad.model';
import { environment } from 'src/environments/environment';
import { Vehicle } from '../model/vehicle.model';
import { PriceList } from '../model/price-list.model';

@Injectable({
  providedIn: 'root'
})
export class PriceListService {

  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/price-list';

  constructor(private http: HttpClient) { }

  public all() : Observable<PriceList[]> {
    return this.http.get<PriceList[]>(this.baseUrl);
  }

  public get(id: number) : Observable<PriceList> {
    return this.http.get<PriceList>(this.baseUrl + '/' + id);
  }
}