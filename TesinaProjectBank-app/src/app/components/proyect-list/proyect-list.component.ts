import { Component, OnInit } from '@angular/core';
import { Proyect } from '../../models/proyect';
import { ProyectService } from 'src/app/services/proyect.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-proyect-list',
  templateUrl: './proyect-list.component.html',
  styleUrls: ['./proyect-list.component.css']
})
export class ProyectListComponent implements OnInit {

  proyects:Proyect[]=[];
  roles: string[]=[];
  isAdmin=false;
  
  constructor( 
    private proyectService:ProyectService,
    private tokenService:TokenService
    ) { }

  ngOnInit(): void {
    this.isAdmin=this.tokenService.getIsAdmin();
    this.proyectService.getProyects().subscribe(data=>{
      this.proyects=data;
    })
  }

}
