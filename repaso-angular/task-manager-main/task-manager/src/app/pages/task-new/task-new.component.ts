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
    title: this.fb.nonNullable.control('', [
      Validators.required,
      Validators.minLength(3),
    ]),
    description: this.fb.nonNullable.control('', [
      Validators.minLength(5),
    ]),
    completed: this.fb.nonNullable.control(false),
  });

  save() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const data = this.form.getRawValue();
    this.tasks.create(data).subscribe({
      next: () => {
        this.router.navigateByUrl('/tareas');
      },
      error: error => console.error(error)
    });
  }

  cancel() {
    this.router.navigateByUrl('/tareas');
  }
}
