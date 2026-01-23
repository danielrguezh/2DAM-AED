import { AuthApiService } from '../services/auth-api.service';
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(private authApiService: AuthApiService, private router: Router) {}

    canActivate(): boolean {
        if (this.authApiService.isLoggedIn()) {
            return true;
        } else {
            this.router.navigate(['/login']);
            return false;
        }
    }
}