import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TasksApiService } from '../../services/tasks-api.service';

@Component({
  selector: 'app-task-new',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './task-new.component.html',
  styleUrl: './task-new.component.css',
})
export class TaskNewComponent {
  private fb = inject(FormBuilder);
  private tasks = inject(TasksApiService);
  private router = inject(Router);

  form = this.fb.nonNullable.group({
    titulo: this.fb.nonNullable.control('', [Validators.required, Validators.minLength(3)]),
    descripcion: this.fb.nonNullable.control('',[Validators.minLength(5)]),
    completada: this.fb.nonNullable.control(false),
  });

  save() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
  
    const data = this.form.getRawValue();
    console.log('ENVIANDO AL BACKEND:', data);
  
    this.tasks.create(data).subscribe({
      next: res => {
        console.log('RESPUESTA BACKEND:', res);
        this.router.navigateByUrl('/tareas');
      },
      error: err => console.error(err)
    });
  }

  cancel() {
    this.router.navigateByUrl('/tareas');
  }
}