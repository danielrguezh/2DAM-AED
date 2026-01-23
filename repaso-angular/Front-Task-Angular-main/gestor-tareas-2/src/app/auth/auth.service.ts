import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { Auth } from "../models/auth.model";
import { Injectable } from "@angular/core";
import { AuthResponse } from "../auth/auth.models";



@Injectable({ providedIn: 'root' })
export class AuthApiService {
    private readonly TOKEN_KEY = 'token';
    private baseUrl = 'http://localhost:8080/api/v1/auth/login';
    constructor(private http: HttpClient) { }
    private token = "";


    login(user: Auth): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(this.baseUrl, user).pipe(
          tap(res => {
            localStorage.setItem(this.TOKEN_KEY, res.token);
            })
        );
    }

    setToken(response: any) {
        const tokenString = response.token || response;
    
        if (typeof tokenString === 'string') {
          localStorage.setItem('auth_token', tokenString);
        } else {
          console.error('El token recibido no es un string:', response);
        }
    }
    
    getToken(): string | null {
        return localStorage.getItem(this.TOKEN_KEY);
    }

    logout() {
        localStorage.removeItem(this.TOKEN_KEY);
    }

    isLoggedIn(): boolean {
        return !!this.getToken();
    }

}