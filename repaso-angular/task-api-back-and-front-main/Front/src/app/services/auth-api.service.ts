import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { LoginRequest, LoginResponse } from "../models/auth.model";
import { Injectable } from "@angular/core";

@Injectable({ providedIn: 'root' })
export class AuthApiService {
    private readonly TOKEN_KEY = 'token';
    private baseUrl = 'http://localhost:8080/api/v1/auth/login';
    constructor(private http: HttpClient) { }

    login(user: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(this.baseUrl, user).pipe(
          tap(res => {
            localStorage.setItem(this.TOKEN_KEY, res.token);
          })
        );
      }

    getToken(): string | null {
        return localStorage.getItem(this.TOKEN_KEY);
    }

    setToken(token: string): void{
        localStorage.setItem(this.TOKEN_KEY, token);
    }

    logout(): void {
        localStorage.removeItem(this.TOKEN_KEY);
    }

    isLoggedIn(): boolean {
        return this.getToken() !== null;
    }

}