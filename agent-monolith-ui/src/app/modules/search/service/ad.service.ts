import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ad } from '../model/ad.model';
import { environment } from 'src/environments/environment';
import { AdLocation } from '../model/ad-location.model';

@Injectable({
  providedIn: 'root'
})
export class AdService {

  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/ads';

  constructor(private http: HttpClient) { }

  public all() : Observable<Ad[]> {
    return this.http.get<Ad[]>(this.baseUrl);
  }

  public get(id: number) : Observable<Ad> {
    return this.http.get<Ad>(this.baseUrl + '/' + id);
  }

  public getLocations() : Observable<AdLocation[]> {
    return this.http.get<AdLocation[]>(this.baseUrl + '/location');
  }

  
}