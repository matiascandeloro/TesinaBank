import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { AdminComponent } from './components/admin/admin.component';
import { LoginComponent } from './components/login/login.component';

//Modules
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { tokenInterceptorProvider, TokenInterceptorService } from './services/token-interceptor.service';
import { ProyectListComponent } from './components/proyect-list/proyect-list.component';
import { ProyectItemComponent } from './components/proyect-item/proyect-item.component';
import { ProyectAddComponent } from './components/proyect-add/proyect-add.component';
import { ConfigService } from './services/config.service';
import { MenuComponent } from './components/menu/menu.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LogoutComponent } from './components/logout/logout.component';
import { ProyectViewerComponent } from './components/proyect-viewer/proyect-viewer.component';
import { ModalBasicComponent } from './components/modal-basic/modal-basic.component';
import { AppliedProyectComponent } from './components/applied-proyect/applied-proyect.component';
import { ProyectSearchedListComponent } from './components/proyect-searched-list/proyect-searched-list.component';
import { ModalComponent } from './components/modal/modal.component';
import { UserAbmComponent } from './components/user-abm/user-abm.component';
import { UserViewerComponent } from './components/user-viewer/user-viewer.component';
import { UserAddComponent } from './components/user-add/user-add.component';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AdminComponent,
    LoginComponent,
    ProyectListComponent,
    ProyectItemComponent,
    ProyectAddComponent,
    MenuComponent,
    LogoutComponent,
    ProyectViewerComponent,
    ModalBasicComponent,
    AppliedProyectComponent,
    ProyectSearchedListComponent,
    ModalComponent,
    UserAbmComponent,
    UserViewerComponent,
    UserAddComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule
  ],
  providers: [
    //JWT
    {provide: JWT_OPTIONS,useValue: JWT_OPTIONS},
    JwtHelperService,
    tokenInterceptorProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
