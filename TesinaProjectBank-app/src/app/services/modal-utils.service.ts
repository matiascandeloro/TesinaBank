import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ModalUtilsService {

  constructor() { }

  modalState =new EventEmitter<any>();
  titulo=new EventEmitter<string>();
  descripcion =new EventEmitter<string>();
}
