import { Component, OnInit } from '@angular/core';
import { Task } from '../../models/task.model';
import { TasksApiService } from '../../services/tasks-api.service';

@Component({
  selector: 'app-tasks',
  standalone: true,
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css',
})
export class TasksComponent implements OnInit {
  tasks: Task[] = [];
  error = '';

  constructor(public tasksApiService: TasksApiService) {}

  ngOnInit() {
    this.loadTasks();
  }

  loadTasks() {
    this.tasksApiService.list().subscribe({
      next: (tasks) => (this.tasks = tasks),
      error: () => (this.error = 'Error cargando las tareas'),
    });
  }

  toggleCompleted(task: Task) {
    this.tasksApiService.toggleCompleted(task).subscribe({
      next: () => this.loadTasks(),
      error: () => (this.error = 'Error actualizando la tarea'),
    });
  }

  remove(id: number) {
    this.tasksApiService.remove(id).subscribe({
      next: () => this.loadTasks(),
      error: () => (this.error = 'Error eliminando la tarea'),
    });
  }
}
