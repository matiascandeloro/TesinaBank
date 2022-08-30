import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { User } from '../models/user';
import { Userdata } from '../models/userdata';

const TOKEN_KEY='AuthToken';

@Injectable({
    providedIn: 'root'
  })

export class TokenService {

  roles: Array<string>=[];
  private ret:boolean=false;
  user:Userdata=new Userdata();
  token:string="";

  constructor(
    private jwtHelper: JwtHelperService,
    private router:Router
    ){}

  public setToken(token: any):void{
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY,token);
  }
  
  public getToken(): string | null{
    if (window.localStorage.getItem(TOKEN_KEY)=='undefined' || window.localStorage.getItem(TOKEN_KEY)==undefined ){
      window.localStorage.removeItem(TOKEN_KEY);
    }
    if (window.localStorage.getItem(TOKEN_KEY)!=""){
      return window.localStorage.getItem(TOKEN_KEY);
    }
    return null;
  }

  public getUserName(): string {
    if (this.isLogged()){
      return '';
    }
    const token=this.getToken();
    const payload= token?.split('.')[1] as string;
    const payloadDecoded=atob(payload);
    const values=JSON.parse(payloadDecoded);
    const username=values.sub;
    return username;
  }

  isLogged():boolean{

    if (this.getToken()!=null){
      const exp=JSON.parse(atob(this.getToken()?.split('.')[1]as string));
      const expirationTime:Date=new Date(exp.exp*1000);
      const actualTime:Date= new Date();
      if (expirationTime>actualTime){ 
        return true;
      }else{
        this.logOut();
      }
    }
    return false;
  }


  public getIsAdmin(): boolean {
    if (!this.isLogged()){
      return false;
    }
    const token=this.getToken();
    const payload= token?.split('.')[1] as string;
    const payloadDecoded=atob(payload);
    const values=JSON.parse(payloadDecoded);
    const roles=values.authorities;
    this.ret=false;
    roles.forEach((rol:any)=>{
      if (rol.authority==='ROLE_ADMIN'){
        this.ret=true;
      }
    })
    
    return this.ret;
  }

  public getIsStudent(): boolean {
    if (!this.isLogged()){
      return false;
    }
    const token=this.getToken();
    const payload= token?.split('.')[1] as string;
    const payloadDecoded=atob(payload);
    const values=JSON.parse(payloadDecoded);
    const roles=values.authorities;
    this.ret=false;
    roles.forEach((rol:any)=>{
      if (rol.authority==='ROLE_STUDENT'){
        this.ret=true;
      }
    })
    
    return this.ret;
  }

  public getIsOfferer(): boolean {
    if (!this.isLogged()){
      return false;
    }
    const token=this.getToken();
    const payload= token?.split('.')[1] as string;
    const payloadDecoded=atob(payload);
    const values=JSON.parse(payloadDecoded);
    const roles=values.authorities;
    this.ret=false;
    roles.forEach((rol:any)=>{
      if (rol.authority==='ROLE_OFFERER'){
        this.ret=true;
      }
    })
    
    return this.ret;
  }
  // hacer una sola funcion

  public getUserId():number{
    //userdataid
    const token=this.getToken();
    const payload= token?.split('.')[1] as string;
    const payloadDecoded=atob(payload);
    const values=JSON.parse(payloadDecoded);
    return values.userdataid as number;
  }

  public logOut():void{
    window.localStorage.clear();
    this.router.navigate(['/']);
  }

  public getUserdata():Userdata{
    
    this.user.id=this.getUserId();
    this.user.name=this.getUserName();
    this.user.lastname='';
    this.user.mail='';
    return this.user;
  }
}