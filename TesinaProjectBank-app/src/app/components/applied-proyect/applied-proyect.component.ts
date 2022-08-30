import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Proyect } from 'src/app/models/proyect';
import { ProyectService } from 'src/app/services/proyect.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-applied-proyect',
  templateUrl: './applied-proyect.component.html',
  styleUrls: ['./applied-proyect.component.css']
})
export class AppliedProyectComponent implements OnInit {
  isStudent:boolean=false;
  proyects:Proyect[]=[];
  hasError:boolean=false;
  msjError:string="";
  loading:boolean=false;
  constructor(
    private router:Router,
    private route: ActivatedRoute,
    private proyectService: ProyectService,
    private tokenService: TokenService
  ) { }

  ngOnInit(): void {
    this.hasError=false;
    this.msjError="";
    this.loading=true;
    this.isStudent=this.tokenService.getIsStudent();
    this.proyectService.proyectApplied().subscribe(data=>{
      this.proyects=data;
      this.loading=false;
    },
    err=>{
      this.hasError=true;
      this.msjError=err.error.text;
      this.loading=false;
    });
  }

}
