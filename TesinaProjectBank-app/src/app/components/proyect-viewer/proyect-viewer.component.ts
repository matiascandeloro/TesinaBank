import { Token, Type } from '@angular/compiler';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import * as saveAs from 'file-saver';
import { Proyect } from 'src/app/models/proyect';
import { ProyectDoc } from 'src/app/models/proyectdoc';
import { ModalUtilsService } from 'src/app/services/modal-utils.service';
import { ProyectService } from 'src/app/services/proyect.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-proyect-viewer',
  templateUrl: './proyect-viewer.component.html',
  styleUrls: ['./proyect-viewer.component.css']
})
export class ProyectViewerComponent implements OnInit {

  proyect_item:Proyect=new Proyect();
  isStudent:boolean=false;

  constructor(
    private router:Router,
    private route: ActivatedRoute,
    private modalService: NgbModal,
    private proyectService: ProyectService,
    private tokenService: TokenService,
    private modalCustomService: ModalUtilsService
  ) { }

  ngOnInit(): void {   
    this.isStudent=this.tokenService.getIsStudent();
    if (this.route.snapshot.paramMap.get('item')!=null){
      this.proyect_item= JSON.parse(this.route.snapshot.paramMap.get('item') as string);
      console.log(this.proyect_item)
    }
  }

  open(content: any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result:any) => {
      this.onApply();
    }, (reason:any) => {
      
    });
  }

  onApply(){
    this.proyectService.applyToProyect(this.proyect_item,this.tokenService.getUserdata()).subscribe(data=>{},
    error=>{
      this.openModal(error.error.text);
      }
    );
  }
  onBack(){
    this.router.navigate(['/home']);
  }

  download(doc: ProyectDoc){

    this.proyectService.download(doc.proyectid+"_"+doc.filename).subscribe(
      blob => saveAs(blob, doc.filename) );
  }

  openModal(data:string){
    this.modalCustomService.titulo.emit("Aviso");
    this.modalCustomService.descripcion.emit(data); 
    this.modalCustomService.modalState.emit("block");
  }
}
