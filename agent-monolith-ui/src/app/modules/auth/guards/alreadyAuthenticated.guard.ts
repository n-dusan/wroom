import { AuthService } from '../service/auth.service';
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable()
export class AlreadyAuthenticatedGuard implements CanActivate {

    constructor(private router: Router, private authService: AuthService) {
    }

    canActivate(): boolean {
        if (!this.authService.getToken()) {
            return true;
        } else {
            this.router.navigate(['/home']);
            return false;
        }
    }
}