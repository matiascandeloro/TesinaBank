import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { Config } from 'src/app/models/config';
import { ConfigService } from 'src/app/services/config.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  adminConfig:Config=new Config();

  constructor(
    private configService:ConfigService,
    private router:Router,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.configService.getConfig().subscribe(data=>{
      this.adminConfig=data;
    })
  }

  onSubmit(){
    this.configService.addProyect(this.adminConfig).subscribe();
    this.router.navigate(['/home']);
  }

  open(content: any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.onSubmit();
    }, (reason) => {
      
    });
  }
}
