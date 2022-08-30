import { Component, Input, OnInit, Output } from '@angular/core';
import { Proyect } from 'src/app/models/proyect';
import { EventEmitter } from '@angular/core';
import { TokenService } from 'src/app/services/token.service';
import { Router } from '@angular/router';
import { ProyectService } from 'src/app/services/proyect.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-proyect-item',
  templateUrl: './proyect-item.component.html',
  styleUrls: ['./proyect-item.component.css']
})
export class ProyectItemComponent implements OnInit {

  @Input() proyect_item:Proyect=new Proyect();
  @Output() deleteItem: EventEmitter<Proyect>= new EventEmitter();

  isAdmin:boolean=false;
  isStudent:boolean=false;
  isOfferer:boolean=false;

  constructor(
    private tokenService: TokenService,
    private router:Router, 
    private proyectService:ProyectService,
    private modalService: NgbModal
    ) { }

  ngOnInit(): void {
    this.isAdmin=this.tokenService.getIsAdmin();
    this.isStudent=this.tokenService.getIsStudent();
    this.isOfferer=this.tokenService.getIsOfferer() && this.proyect_item.userdata.id== this.tokenService.getUserId();
    
  }

  open(content: any,proy:Proyect) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result:any) => {
      this.onDelete(proy);
    }, (reason:any) => {
      
    });
  }

  onDelete(proyect_item:Proyect){
    this.proyectService.delProyect(proyect_item).subscribe();
    this.router.navigate(['/home']).then(()=>{window.location.reload();});
  }
  onEdit(proyect_item:Proyect){
    this.router.navigate(["/addproyect",{'item':JSON.stringify(proyect_item)}]);
  }
  onOpen(proyect_item:Proyect){
    this.router.navigate(["/viewproyect",{'item':JSON.stringify(proyect_item)}]);
  }
} 
