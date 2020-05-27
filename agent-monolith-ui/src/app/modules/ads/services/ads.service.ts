import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment'
import { Observable } from 'rxjs';
import { Location } from '../model/location.model';
import { HttpClient } from '@angular/common/http';

@Injectable({providedIn: 'root'})
export class AdsService {

  private url = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/ads';

  constructor(private http: HttpClient) {}

  getAllLocations() : Observable<Location[]> {
    return this.http.get<Location[]>(this.url + '/location');
  }

  createLocation(location: Location) : Observable<Location> {
    console.log('test' + this.url + '/location/test')
    console.log('loc', location)
    return this.http.post<Location>(this.url + '/location', location);
  }
}
