import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthApiService } from '../../auth/auth.service';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  
private fb = inject(FormBuilder);
  private auth = inject(AuthApiService);
  private router = inject(Router);
  error: string | null = null;


  
  form = this.fb.nonNullable.group({
    username: this.fb.nonNullable.control('', [Validators.required]),
    password: this.fb.nonNullable.control('',[Validators.required]),
  });

  login() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    } 
    const data = this.form.getRawValue();
    this.auth.login(data).subscribe({
      next: resToken => {
        this.router.navigateByUrl('/tareas');
        this.auth.setToken(resToken);
      },
      error: () => {
        this.error = 'Credenciales inválidas';
      }
    });
  }

  cancel() {
    this.router.navigateByUrl('/tareas');
  }
}
