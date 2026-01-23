import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Task } from '../../models/task.model';
import { TasksApiService } from '../../services/tasks-api';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './tasks.html',
  styleUrl: './tasks.css',
})
export class Tasks implements OnInit {
  tasks: Task[] = [];
  errorMsg: string | null = null;
  loading = false;

  constructor(public api: TasksApiService) { }

  ngOnInit(): void {
    this.load();
  }

  load() {
    this.loading = true;
    this.errorMsg = null;

    this.api.list().subscribe({
      next: data => {
        this.tasks = data;
        this.loading = false;
      },
      error: (e: Error) => {
        this.tasks = [];
        this.errorMsg = e.message;
        this.loading = false;
      }
    });
  }

  toggle(t: Task) {
    const toggled = !t.completada;
    const { id, ...data } = t;

    this.api.update(id, { ...data, completada: toggled }).subscribe({
      next: () => {
        t.completada = toggled;
      },
      error: (e) => {
        alert('Error al actualizar la tarea');

        t.completada = !toggled;
      }
    });
  }

  remove(id: number) {
    this.errorMsg = null;

    this.api.remove(id).subscribe({
      next: () => this.load(),
      error: (e: Error) => this.errorMsg = e.message
    });
  }
}
