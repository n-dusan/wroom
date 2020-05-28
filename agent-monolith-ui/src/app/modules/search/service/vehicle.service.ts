import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ad } from '../model/ad.model';
import { environment } from 'src/environments/environment';
import { Vehicle } from '../model/vehicle.model';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/vehicle';

  constructor(private http: HttpClient) { }

  public all() : Observable<Vehicle[]> {
    return this.http.get<Ad[]>(this.baseUrl + '/all');
  }

  public get(id: number) : Observable<Vehicle> {
    return this.http.get<Ad>(this.baseUrl + '/' + id);
  }
}