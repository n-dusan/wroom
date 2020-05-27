import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ad } from '../model/ad.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdService {

  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/ad';

  constructor(private http: HttpClient) { }

  public all() : Observable<Ad[]> {
    return this.http.get<Ad[]>(this.baseUrl);
  }
}
