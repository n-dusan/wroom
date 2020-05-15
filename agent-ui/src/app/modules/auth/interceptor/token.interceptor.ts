import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { UserTokenState } from '../model/user-token-state.model';
import { Observable } from 'rxjs';
import { LoggedUser } from '../model/logged-user.model';
import { AuthService } from '../service/auth.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
    loggedUser: LoggedUser;
    userTokenState: UserTokenState;

    constructor(public authService: AuthService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.loggedUser = JSON.parse(localStorage.getItem("LoggedUser"));

        if (this.loggedUser) {
            this.userTokenState = this.loggedUser.token;
            if (this.userTokenState) {
                request = request.clone({
                    setHeaders: {
                        Authorization: `Bearer ${this.userTokenState.token}`
                    }
                });
            }
        }

        console.log("I'm sending a request", request)
        return next.handle(request);
    }
}