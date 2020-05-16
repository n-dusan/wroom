import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
    token: string = "";

    constructor() { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.token = JSON.parse(localStorage.getItem("token"));

        if(this.token) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${this.token}`
                }
            })
        }

        // console.log("I'm sending a request", request)
        return next.handle(request);
    }
}