import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TasksComponent } from './pages/tasks/tasks.component';
import { TaskNewComponent } from './pages/task-new/task-new.component';
import { AboutComponent } from './pages/about/about.component';
import { LoginComponent } from './pages/login/login.component';
import { RedirectComponent } from './pages/redirect/redirect.component';
import { AuthGuard } from './guards/auth.guard';
import { PublicGuard } from './guards/public.guard';

export const routes: Routes = [
  // Rutas públicas
  { path: 'login', component: LoginComponent, canActivate: [PublicGuard] },
  
  // Rutas privadas (protegidas por AuthGuard)
  { path: 'tareas', component: TasksComponent, canActivate: [AuthGuard] },
  { path: 'tareas/nueva', component: TaskNewComponent, canActivate: [AuthGuard] },
  
  // Otras rutas
  { path: 'acercaDe', component: AboutComponent },
  { path: 'home', component: HomeComponent },
  
  // Redirección raíz
  { path: '', component: RedirectComponent },
  
  // Wildcard
  { path: '**', redirectTo: '' },
];