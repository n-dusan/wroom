import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../../environments/environment'
import { PriceList } from '../../shared/models/price-list.model';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { AuthService } from '../../auth/service/auth.service';
import { LoggedUser } from '../../auth/model/logged-user.model';

@Injectable({providedIn: 'root'})
export class PriceListService {

  private url = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + environment.adsService + '/price-list';

  constructor(private http: HttpClient, private authService: AuthService) {}

  findAllActive(): Observable<PriceList[]> {
    return this.authService.whoami().pipe(switchMap((user: LoggedUser) =>
      this.http.get<PriceList[]>(this.url + '/'+user.id+'/all').pipe(catchError(this.handleException))));
  }

  findById(id: number): Observable<PriceList> {
    return this.http.get<PriceList>(this.url + `/${id}`).pipe(catchError(this.handleException));
  }

  create(priceList: PriceList): Observable<PriceList> {
    return this.http.post<PriceList>(this.url, priceList).pipe(catchError(this.handleException));
  }

  update(priceList: PriceList): Observable<PriceList> {
    return this.http.put<PriceList>(this.url + `/${priceList.id}`, priceList).pipe(catchError(this.handleException));
  }

  remove(id: number): Observable<String> {
    return this.http.delete(this.url + '/' + id, { responseType: 'text' }).pipe(catchError(this.handleException));
  }

  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.error);
  }

}
