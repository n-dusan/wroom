import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GpsService {

  constructor(private http: HttpClient) {}


  public generateJWT(id: number): Observable<any> {
    return this.http.get(environment.gpsService + '/generate/' + id, { responseType: 'text'});
  }
}
