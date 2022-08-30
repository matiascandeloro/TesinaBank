import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Proyect } from 'src/app/models/proyect';
import { ProyectService } from 'src/app/services/proyect.service';
import { environment } from 'src/environments/environment';
import { threadId } from 'worker_threads';
import { Tag } from 'src/app/models/tag';

@Component({
  selector: 'app-proyect-searched-list',
  templateUrl: './proyect-searched-list.component.html',
  styleUrls: ['./proyect-searched-list.component.css']
})
export class ProyectSearchedListComponent implements OnInit {
  proyects:Proyect[]=[];
  formData= new FormData();
  hasError:boolean=false;
  msjError:string="";
  loading:boolean=false;
  constructor(
    private route: ActivatedRoute,
    private proyectService:ProyectService,
    private http:HttpClient
  ) { }

  ngOnInit(): void {
    this.hasError=false;
    this.msjError="";
    this.loading=true;

    console.log(this.route.snapshot.paramMap.get('valueToSearch'))
    console.log(this.route.snapshot.paramMap.get('tagToSearch'))

    if (this.route.snapshot.paramMap.get('valueToSearch')!=null || this.route.snapshot.paramMap.get('tagToSearch')!=null){
      this.proyectService.proyectSearch(this.route.snapshot.paramMap.get('valueToSearch') as string, this.route.snapshot.paramMap.get('tagToSearch') as string).subscribe(data =>{
        this.proyects=data;
        this.loading=false;
      },
      err=>{
        this.hasError=true;
        this.msjError=err.error.text;
        this.loading=false;
      }
      );
    }
  }

}
