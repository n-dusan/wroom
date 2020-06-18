import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { RentReport } from '../../shared/models/rent-report.model';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

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

  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.error);
  }



}
