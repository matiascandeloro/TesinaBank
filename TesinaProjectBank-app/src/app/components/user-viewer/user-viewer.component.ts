import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-viewer',
  templateUrl: './user-viewer.component.html',
  styleUrls: ['./user-viewer.component.css']
})
export class UserViewerComponent implements OnInit {

  user:User=new User();
  rolSet:string="";
  rolesFE:string[]=["Administrador","Estudiante","Oferente"];
  rolesBE:string[]=["ROLE_ADMIN","ROLE_STUDENT","ROLE_OFFERER"]

  constructor(
    private userService:UserService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    if (this.route.snapshot.paramMap.get('item')!=null){
      this.user= JSON.parse(this.route.snapshot.paramMap.get('item') as string);
      this.rolSet=this.rolesFE[this.getIndexRolBE(this.user.roles)];
    }
  }

  onSubmit(){
    this.userService.saveUser(this.user).subscribe(
      data=>{},
      err=>{
        console.log(err.error.text);
      });
     // this.router.navigate(['/users']).then(()=>{window.location.reload();});
  }
  onBack(){
      this.router.navigate(['/users']);
  }
  
  onSelected(value:string){
    this.user.roles=this.rolesBE[this.getIndexRolFE(value)];
    console.log(value);
    console.log(this.user.roles);
  }
  getIndexRolBE(value:string):number{
    for (let i=0;i<this.rolesBE.length;i++){
      if (this.rolesBE[i]==value){
        return i;
      }
    }
    return -1;
  }
  getIndexRolFE(value:string):number{
    for (let i=0;i<this.rolesFE.length;i++){
      if (this.rolesFE[i]==value){
        return i;
      }
    }
    return -1;
  }
}
