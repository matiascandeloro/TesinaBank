import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor{

  constructor( private tokenService:TokenService ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let intreq=req;
    const token= this.tokenService.getToken();
    if (token!= null){
      intreq=req.clone({
        headers:req.headers.set(
          'Authorization', 'Bearer ' + token)
      });
    }
    return next.handle(intreq);
  }
}
export const tokenInterceptorProvider=[{provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi:true}];
