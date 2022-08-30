import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Proyect } from '../models/proyect';
import { HttpClient } from '@angular/common/http';
import { ConfigService } from './config.service';
import { environment } from 'src/environments/environment';
import { TokenService } from './token.service';
import { ProyectDoc } from '../models/proyectdoc';
import * as saveAs from 'file-saver';
import { Userdata } from '../models/userdata';
import { Tag } from '../models/tag';
import { StringMap } from '@angular/compiler/src/compiler_facade_interface';

@Injectable({
  providedIn: 'root'
})
export class ProyectService {
  formData= new FormData(); 
  constructor(
    private http:HttpClient,
    private tokenService:TokenService
  ) { }

  getProyects():Observable<Proyect[]>{
    return this.http.post<Proyect[]>(environment.URL+'/proyects','');
  }

  addProyect(proyect:Proyect):Observable<Proyect>{
    return this.http.post<Proyect>(environment.URL+'/saveProyect',JSON.parse(JSON.stringify(proyect)));
  }

  //delete
  delProyect(proyect:Proyect):Observable<Proyect>{
    return this.http.post<Proyect>(environment.URL+'/deleteProyect',JSON.parse(JSON.stringify(proyect)));
  }

  download(file: string | undefined): Observable<Blob> {
    return this.http.get(environment.URL+"/"+file, {
      responseType: 'blob'
    });
  }
  deleteDoc(proyectDoc:ProyectDoc):Observable<ProyectDoc>{
    return this.http.post<ProyectDoc>(environment.URL+'/deleteFile',JSON.parse(JSON.stringify(proyectDoc)));
  }

  applyToProyect(proyect:Proyect, userdata:Userdata){
    this.formData= new FormData(); 
    this.formData.append("proyectid",proyect.id.toString());
    this.formData.append("userid",userdata.id.toString());
    return  this.http.post<ProyectDoc>(environment.URL+'/applicantProyect',this.formData);
  }

  proyectApplied(){
    this.formData= new FormData(); 
    this.formData.append("userid",this.tokenService.getUserdata().id.toString());
    return  this.http.post<Proyect[]>(environment.URL+'/proyectApplied',this.formData);
  }

  proyectSearch(valueToSearch:string, tags:string){
    let toString:string="";
    JSON.parse(tags).forEach((element:Tag)=>{
      toString+=element.idtags+",";
    })
    this.formData= new FormData(); 
    this.formData.append("valueToSearch",valueToSearch);
    this.formData.append("tagToSearch",toString);
    return  this.http.post<Proyect[]>(environment.URL+'/proyectsSearch',this.formData);
  }

  getTags():Observable<Tag[]>{
    return this.http.post<Tag[]>(environment.URL+'/tags','');
  }
}
