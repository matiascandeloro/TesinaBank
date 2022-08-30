import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  getUsers():Observable<User[]>{
    return this.http.post<User[]>(environment.URL+'/users','');
  }
  deleteUser(user:User):Observable<User>{
    return this.http.post<User>(environment.URL+'/deleteUser',user);
  }
  saveUser(user:User):Observable<User>{
    return this.http.post<User>(environment.URL+'/saveUser',user);
  }
  userExist(user:User):Observable<User>{
    return this.http.post<User>(environment.URL+'/existsUser',user);
  }
}
