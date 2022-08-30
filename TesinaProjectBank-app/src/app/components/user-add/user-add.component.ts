import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { ModalUtilsService } from 'src/app/services/modal-utils.service';
import { UserService } from 'src/app/services/user.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})
export class UserAddComponent implements OnInit {

  user:User=new User();
  passConf:string="";
  isError:boolean=false;
  errorMsj:string="";
  usernameExists:boolean=false;
  modalRet:any;
  mail:string="";

  constructor(
    private userService:UserService,
    private router:Router,
    private modalService:ModalUtilsService
  ) { }

  ngOnInit(): void {
  }

  onSubmit(){
    
    this.isError=false;
    this.onChange();
    if (this.checkRequired() && !this.usernameExists){
      if (this.user.password==this.passConf){
        this.user.roles="ROLE_STUDENT";
        this.user.active=true;
        this.userService.saveUser(this.user).subscribe(data=>{
         //agregar modal y redireccionar
         this.modalService.titulo.emit("Alta usuario");
         this.modalService.descripcion.emit("El usuario fue generado exitosamente.");
         this.modalService.modalState.emit("block");
         this.modalRet = this.modalService.modalState.subscribe(data=>{
          if (this.modalRet="none"){
            this.router.navigate(['login']);
          }
         });
          
        },err=>{
          console.log(err.error.text);
        });
      }else{
        this.errorMsj="La contraseña no es la misma.";
        this.isError=true;
      }
    }
  }

  checkRequired():boolean{
    let ret:boolean=true;
    if (this.user.mail=='' || !this.isValidEmail(this.user.mail)){
      this.errorMsj="El mail esta vacio o no es valido";
      ret=ret && false;
    }   
    if (this.user.lastname==''){
      this.errorMsj="El apellido esta vacio";
      ret=ret && false;
    }
    if (this.user.name==''){
      this.errorMsj="El nombre esta vacio";
      ret=ret && false;
    }
    if (this.user.password==''){
      this.errorMsj="La reconfirmacion de contraseña esta vacia";
      ret=ret && false;
    }
    if (this.user.password==''){
      this.errorMsj="La contraseña esta vacia";
      ret=ret && false;
    }
    if (this.user.username==''){
      this.errorMsj="El nombre de usuario esta vacio";
      ret=ret && false;
    }
    this.isError=!ret;
    return ret;
  }

  onChange(){
    this.isError=false;
    this.usernameExists=false;
    this.userService.userExist(this.user).subscribe(
      data=>{
        if (data!=null){
          this.errorMsj="El usuario ya existe.";
          this.isError=true;
          this.usernameExists=true;
        }
      },
      err=>{
      }
    );
  }

  public isValidEmail(emailString: string): boolean {
    try {
      let pattern = new RegExp("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$");
      let valid = pattern.test(emailString);
      return valid;
    } catch (TypeError) {
      return false;
    }
  }
  
}
