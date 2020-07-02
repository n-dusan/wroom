import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { RentReport } from '../../shared/models/rent-report.model';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Debt } from '../../shared/models/debt.model';
import { User } from '../../shared/models/user.model';

@Injectable({
  providedIn: 'root'
})
export class RentReportService {

  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/report';

  constructor(private http: HttpClient) {}

  create(report: RentReport): Observable<RentReport> {
    console.log('my report', report)
    return this.http.post<RentReport>(this.baseUrl, report).pipe(catchError(this.handleException));
  }


  findAll(): Observable<RentReport[]> {
    return this.http.get<RentReport[]>(this.baseUrl).pipe(catchError(this.handleException));
  }

  getForVehicle(vehicleId: number): Observable<RentReport[]> {
    return this.http.get<RentReport[]>(this.baseUrl + '/chart/' + vehicleId).pipe(catchError(this.handleException));
  }

  debts(report: RentReport): Observable<RentReport>{
    return this.http.post<RentReport>(this.baseUrl + '/debts', report);
  }

  alldebts(): Observable<Debt[]> {
    return this.http.get<Debt[]>(this.baseUrl + '/alldebts');
  }

  payDebt(id: number): Observable<Debt[]> {
    return this.http.put<Debt[]>(this.baseUrl + '/pay/' + id, {});
  }

  checkDebts(){
    return this.http.get(this.baseUrl + '/checkDebts');
  }

  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.error);
  }

  

}
