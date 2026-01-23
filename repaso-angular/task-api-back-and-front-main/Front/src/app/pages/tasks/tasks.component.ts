import { Component } from '@angular/core';
import { TasksApiService } from '../../services/tasks-api.service';
import { Task } from '../../models/task.model';

@Component({
  selector: 'app-tasks',
  standalone: true,
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css',
})
export class TasksComponent {
  tasks: Task[] = [];
  loading = true;
  error: string | null = null;

  constructor(public tasksService: TasksApiService) { }

  ngOnInit(): void {
    this.tasksService.list().subscribe({
      next: (data) => {
        this.tasks = data;
        this.loading = false;
      },
      error: () => {this.error = "No se pudieron cargar las tareas"; this.loading = false},
    });
  }

  completed(task: Task) {
    const updatedTask = { ...task, completada: !task.completada };
    this.tasksService.update(updatedTask).subscribe({
      next: () => this.tasks = this.tasks.map(t => {
        if (t.id === task.id) {
          return updatedTask;
        }
        return t;
      }),
      error: () => this.error = 'No se pudo actualizar la tarea',
    });
  }

  remove(id: number) {
    this.tasksService.remove(id).subscribe({
      next: () => this.tasks = this.tasks.filter(t => t.id !== id),
      error: () => this.error = 'No se pudo eliminar la tarea',
    });
  }
}