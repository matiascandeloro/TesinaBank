import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './components/admin/admin.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { ProyectAddComponent } from './components/proyect-add/proyect-add.component';
import { ProyectGuard } from './guards/proyect.guard';
import { ProyectViewerComponent } from './components/proyect-viewer/proyect-viewer.component';
import { AppliedProyectComponent } from './components/applied-proyect/applied-proyect.component';
import { ProyectSearchedListComponent } from './components/proyect-searched-list/proyect-searched-list.component';
import { ModalComponent } from './components/modal/modal.component';
import { UserAbmComponent } from './components/user-abm/user-abm.component';
import { UserViewerComponent } from './components/user-viewer/user-viewer.component';
import { UserAddComponent } from './components/user-add/user-add.component';

const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'register', component: UserAddComponent},
  {path:'logout', component: LogoutComponent},
  {path:'modal', component: ModalComponent},
  {path:'home', component: HomeComponent, canActivate: [ProyectGuard], data: {expectedRol:['admin','student','offerer']}},
  {path:'admin', component: AdminComponent, canActivate: [ProyectGuard], data: {expectedRol:['admin']}},
  {path:'addproyect', component: ProyectAddComponent, canActivate: [ProyectGuard], data: {expectedRol:['admin','offerer']}},
  {path:'viewproyect', component: ProyectViewerComponent,canActivate: [ProyectGuard], data: {expectedRol:['admin','student','offerer']}},
  {path:'apliedProyect', component: AppliedProyectComponent, canActivate: [ProyectGuard], data: {expectedRol:['student']}},
  {path:'proyectsearched', component: ProyectSearchedListComponent, canActivate: [ProyectGuard], data: {expectedRol:['admin','student','offerer']}},
  {path:'users', component: UserAbmComponent, canActivate: [ProyectGuard], data: {expectedRol:['admin']}},
  {path:'usersView', component: UserViewerComponent, canActivate: [ProyectGuard], data: {expectedRol:['admin']}},
  {path:'**', pathMatch: 'full', redirectTo:'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
