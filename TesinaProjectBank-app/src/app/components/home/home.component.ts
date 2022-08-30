import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from 'src/app/services/auth.service';
import { ModalUtilsService } from 'src/app/services/modal-utils.service';
import { TokenService } from 'src/app/services/token.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  modal:boolean= false;
  display:string="none";
  constructor( ) { }

  ngOnInit(): void {
  }
  
  

}
