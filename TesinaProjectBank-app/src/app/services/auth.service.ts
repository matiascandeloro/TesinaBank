import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ConfigService } from './config.service';
import { Observable, retry } from 'rxjs';
import { User } from '../models/user';
import { LoginUser } from '../models/login';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private jwtHelper: JwtHelperService
    ) { 

  }
  
  //tipar variables
  singin(user:any){
    return this.http.post(environment.URL+"/authenticate",user);
  }

  isAuth():boolean{
    const token= sessionStorage.getItem('token');
    if (!sessionStorage.getItem('token')|| this.jwtHelper.isTokenExpired(token?.toString())){
      return false;
    }
    return true;
  }
  //no implementado
  public nuevo(newUser: User):Observable<any>{
    return this.http.post<any>(environment.URL+'nuevo',newUser);
  }

  public login(loginUser: LoginUser):Observable<any>{
    return this.http.post<any>(environment.URL+"/authenticate",loginUser);
  }
  
  public refreshToken(token:any):Observable<any>{
    return this.http.post<any>(environment.URL+"/refreshtoken",token);
  }
}
