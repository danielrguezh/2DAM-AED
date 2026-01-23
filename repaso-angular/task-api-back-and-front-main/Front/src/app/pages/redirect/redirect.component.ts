import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthApiService } from '../../services/auth-api.service';

@Component({
  selector: 'app-redirect',
  standalone: true,
  template: '',
})
export class RedirectComponent implements OnInit {
  constructor(private authApiService: AuthApiService, private router: Router) {}

  ngOnInit(): void {
    if (this.authApiService.isLoggedIn()) {
      this.router.navigate(['/tareas']);
    } else {
      this.router.navigate(['/login']);
    }
  }
}
