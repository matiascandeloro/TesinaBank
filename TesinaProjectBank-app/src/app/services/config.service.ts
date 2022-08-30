import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Config } from '../models/config';


@Injectable({
    providedIn: 'root'
})

export class ConfigService {
   
    constructor(
        private http:HttpClient,
    ) { }
    
    getConfig():Observable<Config>{
        return this.http.post<Config>(environment.URL+'/config','');
    }

    addProyect(config:Config):Observable<Config>{
        return this.http.post<Config>(environment.URL+'/saveConfig',JSON.parse(JSON.stringify(config)))
    }
    
}