import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { LoginUser } from 'src/app/models/login';
import { AuthService } from 'src/app/services/auth.service';
import { TokenService } from 'src/app/services/token.service';
import { JwtHelperService } from '@auth0/angular-jwt';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user = {
    username:'',
    password:''
  }
  isLogged=false;
  isLoginFail=false;
  loginUser : LoginUser= new LoginUser('', '');
  roles:string[]|null=[];
  errMsg:string='';
  decoded:any;

  constructor(
    private authService : AuthService,
    private tokenService: TokenService,
    private router: Router,
    private jwtHelper: JwtHelperService
  ) { }

  ngOnInit(): void {
    if (this.tokenService.isLogged()){
      this.isLogged=true;
      this.isLoginFail=false;
    }
  }

  login(){
    this.authService.singin(this.user).subscribe((res:any) =>{
      this.isLogged=true;
      this.isLoginFail=false;
      this.tokenService.setToken(res.jwt);
      this.decoded=this.jwtHelper.decodeToken(res.jwt?.toString());
      this.router.navigate(['home']);
    }, (err:any)=>{
      this.isLogged=false;
      this.isLoginFail=true;
      this.errMsg=err.name;
    }
    )
  }
}
