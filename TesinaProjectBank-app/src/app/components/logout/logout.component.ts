import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(
    private router:Router,
    private tokenService:TokenService
    ) { }

  ngOnInit(): void { 
    this.onLogOut();
    setTimeout(() => { 
      this.router.navigate(['Login']);
    }, 500);
  }
  
  onLogOut(): void {
    this.tokenService.logOut();
  }
}
