import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TasksComponent } from './pages/tasks/tasks.component';
import { TaskNewComponent } from './pages/task-new/task-new.component';
import { AboutComponent } from './pages/about/about.component';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './auth/auth.guard';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'tareas', component: TasksComponent,canActivate: [AuthGuard]  },
  { path: 'tareas/nueva', component: TaskNewComponent,canActivate: [AuthGuard] },
  { path: 'acercaDe',component: AboutComponent,canActivate: [AuthGuard]},
  { path: 'login',component: LoginComponent,canActivate: [AuthGuard]},
  { path: '**', redirectTo: '' },
];