import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment'
import { Observable, throwError, forkJoin, Subject } from 'rxjs';
import { Location } from '../model/location.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, map, switchMap } from 'rxjs/operators';
import { Vehicle } from '../../shared/models/vehicle.model';
import { Ad } from '../model/ad.model';
import { AuthService } from '../../auth/service/auth.service';
import { LoggedUser } from '../../auth/model/logged-user.model';
import { CommentModel } from '../../shared/models/comment-model.model';


@Injectable({providedIn: 'root'})
export class AdsService {

  private adsUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/ads';
  private vehicleUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/vehicle';
  private priceListUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/price-list';

  constructor(private http: HttpClient,
    private authService: AuthService) {}

  createAd(ad: Ad): Observable<Ad> {
    return this.http.post<Ad>(this.adsUrl, ad).pipe(catchError(this.handleException));
  }

  deleteAd(adId: number): Observable<string> {
    return this.http.delete(this.adsUrl + '/' + adId, { responseType: 'text' }).pipe(catchError(this.handleException));
  }

  findAd(id: number): Observable<Ad> {
    return this.http.get(this.adsUrl + '/' + id).pipe(catchError(this.handleException));;
  }

  updateAd(ad: Ad): Observable<Ad> {
    return this.http.put(this.adsUrl + '/' + ad.id, ad).pipe(catchError(this.handleException));
  }
  //makes multiple http requests and returns an array of observables
  requestAdData(ad: Ad): Observable<any[]> {
    let vehicleResponse = this.http.get(this.vehicleUrl + '/' + ad.vehicleId);
    let priceListResponse = this.http.get(this.priceListUrl + '/' + ad.priceListId);
    let locationResponse = this.http.get(this.adsUrl + '/location/' + ad.locationId);

    return forkJoin([vehicleResponse, priceListResponse, locationResponse]).pipe(catchError(this.handleException));
  }

  getAllActiveForUser(userId: number): Observable<Ad[]> {
    return this.http.get<Ad[]>(this.adsUrl + '/all/' + userId);
  }

  getAllLocations() : Observable<Location[]> {
    return this.http.get<Location[]>(this.adsUrl + '/location').pipe(catchError(this.handleException));
  }

  createLocation(location: Location) : Observable<Location> {
    return this.http.post<Location>(this.adsUrl + '/location', location).pipe(catchError(this.handleException));
  }

  findLocation(id: number): Observable<Location> {
    return this.http.get<Location>(this.adsUrl + '/location/' + id).pipe(catchError(this.handleException));
  }


  getAllVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(this.vehicleUrl).pipe(catchError(this.handleException));
  }

  checkAdCount(): Observable<number> {
      return this.authService.whoami().pipe(switchMap((user: LoggedUser) =>
      this.http.get<number>(this.adsUrl + '/count/' + user.id).pipe(catchError(this.handleException))));
  }

  addComment(comment: CommentModel, id: number) : Observable<CommentModel> {
    return this.http.post<CommentModel>(`${this.adsUrl}/comment/` + id, comment);
  }

  getAllComments() : Observable<CommentModel[]> {
    return this.http.get<CommentModel[]>(this.adsUrl + '/comments');
  }

  confirm(id: number) {
    return this.http.post(this.adsUrl + '/confirm/' + id, id);
  }

  refuse(id: number) {
    return this.http.post(this.adsUrl + '/refuse/' + id, id);
  }

  getAllAds(): Observable<Ad[]>{
    return this.http.get<Ad[]>(this.adsUrl + '/allAds');  
  }

  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.error);
  }
}
