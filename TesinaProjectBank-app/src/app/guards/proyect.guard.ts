import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Route, Router, RouterLink, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenService } from '../services/token.service';

@Injectable({
  providedIn: 'root'
})
export class ProyectGuard implements CanActivate {
  
  realRol:string='';
  expectedRolSplit:string[]=[];

  constructor(
    private tokenService:TokenService,
    private router:Router

  ){  }
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot):  boolean  {
      let ret:Boolean=false;
      const expectedRol= route.data['expectedRol'];
      //simple rol -> hacerlo multi rol?
      this.realRol='';
        if (this.tokenService.getIsAdmin()){
          this.realRol='admin';
          ret=ret || (expectedRol.indexOf(this.realRol)!=-1);
        }
        if (this.tokenService.getIsOfferer()){ 
          this.realRol='offerer';
          ret=ret || (expectedRol.indexOf(this.realRol)!=-1);
        }
        if (this.tokenService.getIsStudent()){
          this.realRol='student';
          ret=ret || (expectedRol.indexOf(this.realRol)!=-1);
        }


      if (!this.tokenService.isLogged()||ret==false){
        this.router.navigate(['/']);
        return false;
      }

    return true;
  } 

}
