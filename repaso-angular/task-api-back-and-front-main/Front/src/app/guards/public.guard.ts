import { AuthApiService } from '../services/auth-api.service';
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class PublicGuard implements CanActivate {

    constructor(private authApiService: AuthApiService, private router: Router) {}

    canActivate(): boolean {
        if (this.authApiService.isLoggedIn()) {
            this.router.navigate(['/tareas']);
            return false;
        }
        return true;
    }
}
