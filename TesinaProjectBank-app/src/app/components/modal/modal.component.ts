import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ModalUtilsService } from 'src/app/services/modal-utils.service';


@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {

  display:string="none";
  titulo:string="";
  descripcion:string="";
  @Output() messageEvent = new EventEmitter<string>();

  constructor(
    private modalService:ModalUtilsService

  ) { }

  ngOnInit(): void {
    this.modalService.modalState.subscribe((data:string)=>{
      this.display=data;
    });

    this.modalService.titulo.subscribe((data:string)=>{
      this.titulo=data;
    });
    this.modalService.descripcion.subscribe((data:string)=>{
      this.descripcion=data;
    }); 

  }


  closeModal(){
    this.modalService.modalState.emit("none");
  }
}
