import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { elementAt } from 'rxjs';
import { Proyect } from 'src/app/models/proyect';
import { Tag } from 'src/app/models/tag';
import { AuthService } from 'src/app/services/auth.service';
import { ProyectService } from 'src/app/services/proyect.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  isLogged=false;
  isCollapsed=false;
  isAdmin=false;
  isStudent=false;
  isOfferer=false;
  formData= new FormData();
  tagAvailable: Tag[] =[];
  tagToSearch: Tag[]=[];
  searchValue:string="";
  proyects:Proyect[]=[];
  inter:any;
  showFilter=false;

  constructor(
    private tokenService:TokenService,
    private router:Router,
    private authService:AuthService,
    private proyectService:ProyectService
    ) { }

  ngOnInit(): void {
    if (this.tokenService.isLogged()){
      this.isLogged=true;
      this.isAdmin=this.tokenService.getIsAdmin();
      this.isOfferer=this.tokenService.getIsOfferer();
      this.isStudent=this.tokenService.getIsStudent();
      this.inter=setInterval(()=>this.refreshToken(),1000*30*1);
      this.proyectService.getTags().subscribe(data=>{
        this.tagAvailable=data;
      },
      err=>{
        console.log(err.error.msg)
      }
      );
    }
    
  }

  ngOnDestroy() {
    if (this.inter) {
      clearInterval(this.inter);
    }
  }

  onSubmit(){
    console.log(this.searchValue!=" ");
    console.log(JSON.stringify(this.tagToSearch).length>0);
    if (this.searchValue!=" " && JSON.stringify(this.tagToSearch).length>0) {
      this.router.navigate(["/proyectsearched",{'valueToSearch':this.searchValue,'tagToSearch':JSON.stringify(this.tagToSearch)}]).then(() => {
        window.location.reload();
      });
    }
  }

  repetirCadaSegundo() {
    setInterval(()=>{this.refreshToken},(1000*60*10));
  }

  refreshToken(){
    this.authService.refreshToken(this.tokenService.getToken()).subscribe(data=> {
    },
    err=>{
      this.tokenService.setToken(err.error.text);
    });
  }
  filterTag(){
    this.showFilter=!this.showFilter;
  }

  addTagToSearch(tag: number){
    let add:boolean=true;
    this.tagToSearch.forEach((element:Tag)=>{
      if (element.idtags==tag){
        const index = this.tagToSearch.indexOf(element, 0);
        if (index > -1) {
          this.tagToSearch.splice(index, 1);
        }
        add=false;
      }
    })
    this.tagAvailable.forEach((element:Tag)=>{
      if(element.idtags==tag && add){
        this.tagToSearch.push(element);
      }
    })
  }


  checkTag(tag: number){
    let add:boolean=false;
    this.tagToSearch.forEach((element:Tag)=>{
      if (element.idtags==tag){
        add=true;
      }
    })
    return add;
  }

  deselectAll(){
    this.tagToSearch=[];
  }
  help(){
    let filename="Manual_de_Usuario.pdf";
    this.proyectService.download(filename).subscribe(
      blob => {
      var file = new Blob([blob], {type: 'application/pdf'});
      var fileURL = URL.createObjectURL(file);
      window.open(fileURL);
      });
  }
}
