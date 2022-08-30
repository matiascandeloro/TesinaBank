import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as saveAs from 'file-saver';
import { Proyect } from 'src/app/models/proyect';
import { ProyectDoc } from 'src/app/models/proyectdoc';
import { Tag } from 'src/app/models/tag';
import { ProyectService } from 'src/app/services/proyect.service';
import { TokenService } from 'src/app/services/token.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-proyect-add',
  templateUrl: './proyect-add.component.html',
  styleUrls: ['./proyect-add.component.css']
})
export class ProyectAddComponent implements OnInit {

  itemproyect:Proyect =new Proyect();
  fileName:String[] = []; 
  formData= new FormData();
  formDataTag= new FormData();
  guardar:string="";
  isError:boolean=false;
  errorMsj:string="";
  tagCheck:string="";
  tagAvailable: Tag[] =[];
  tagfilter: Tag[] =[];
  tagToSave:Tag[]=[];

  constructor(
    private proyectService:ProyectService,
    private router:Router,
    private tokenService: TokenService,
    private route: ActivatedRoute,
    private http:HttpClient
    ) { }

  ngOnInit(): void { 
    this.guardar="Crear";
    if (this.route.snapshot.paramMap.get('item')!=null){
      this.guardar="Guardar";
      this.itemproyect= JSON.parse(this.route.snapshot.paramMap.get('item') as string);
    }
    this.proyectService.getTags().subscribe(data=>{
      this.tagAvailable=data;
    },
    err=>{
      console.log(err.error.msg)
    }
    );
  }

  onSubmit(){
    if (this.checkRequired()){
      if (this.itemproyect.userdata.id==0){
        this.itemproyect.userdata.id=this.tokenService.getUserId();
      }
      this.tagToSave=this.itemproyect.proyecttags;
      
      this.proyectService.addProyect(this.itemproyect).subscribe((data:Proyect)=>{
        this.itemproyect=data as Proyect
        this.formData.append("id",this.itemproyect.id.toString());
        this.tagToSave.forEach(element=>{
          this.formDataTag=new FormData();
          this.formDataTag.append("proyectid",this.itemproyect.id.toString());
          this.formDataTag.append("tagname",JSON.parse(JSON.stringify(element.tagname)));
          this.http.post(environment.URL+"/addTagToSave",this.formDataTag).subscribe();          
        }); 
        this.http.post(environment.URL+"/uploadFile",this.formData).subscribe();
        this.router.navigate(['/home']).then(()=>{window.location.reload();});
      });
    }
    
  }
  onBack(){
    this.router.navigate(['/home']);
  }

  onFileChange(event:any) {
    for (var i = 0; i < event.target.files.length; i++) { 
      this.fileName.push(event.target.files[i]);
    }
  }

  onFileSelected(event:Event){
    const target = event.target as HTMLInputElement;
    const files:FileList=target.files as FileList;
    for (let i=0; i< files.length;i++){
      if (files[i]){
        this.fileName.push(files[i].name);
        this.formData.append("files",files[i]); 
      }
    }
  }
  
  download(doc: ProyectDoc){
    this.proyectService.download(doc.proyectid+"_"+doc.filename).subscribe(
      blob => saveAs(blob, doc.filename) );
  }

  deleteDoc(doc: ProyectDoc){
    this.itemproyect.proyectdoc.splice(this.itemproyect.proyectdoc.indexOf(doc),1);
    this.proyectService.deleteDoc(doc).subscribe();
  }

  public isValidEmail(emailString: string): boolean {
    try {
      let pattern = new RegExp("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$");
      let valid = pattern.test(emailString);
      return valid;
    } catch (TypeError) {
      return false;
    }
  }

  checkRequired():boolean{
    let ret:boolean=true;
    if (this.itemproyect.description==''){
      this.errorMsj="La Descripcion esta vacia";
      ret=ret && false;
    }
    if (this.itemproyect.contact_mail=='' || !this.isValidEmail(this.itemproyect.contact_mail)){
      this.errorMsj="El mail esta vacio o no es valido";
      ret=ret && false;
    }   
    if (this.itemproyect.lastname==''){
      this.errorMsj="El apellido esta vacio";
      ret=ret && false;
    }
    if (this.itemproyect.name==''){
      this.errorMsj="El nombre esta vacio";
      ret=ret && false;
    }
    if (this.itemproyect.title==''){
      this.errorMsj="El titulo del proyecto esta vacio";
      ret=ret && false;
    }
    this.isError=!ret;
    return ret;
  }

  checkTag(){
    if (this.tagCheck.length<=2){
      this.tagfilter=[];
    }else{
      if (this.tagCheck.length>2){
        this.tagfilter=this.tagAvailable.filter((obj)=>{
          return obj.tagname.toLocaleLowerCase().includes(this.tagCheck.toLocaleLowerCase());
        });
      }
    }
  }

  onClickTag(element:string){
    
    this.addTagToProyect(element);
    this.tagCheck='';
    this.tagfilter=[];

  }

  addTagToProyect(tag:string){
    let tagItem= new Tag();
    let proyectTag=new Tag();
    
    this.tagAvailable.forEach((data:Tag)=>{
      if (data.tagname===tag){
          tagItem=data;
      }
    });
    proyectTag=tagItem;
    if (!this.containTag(tagItem)){
      this.itemproyect.proyecttags.push(proyectTag) ;
    }
  }

  containTag(tag:Tag){
    let ret:boolean=false;
    this.itemproyect.proyecttags.forEach(data=>{
      if (data.tagname==tag.tagname){
        ret=true;
      }
    });
    return ret;
  }

  delete(tag:string){
    let i=0,ret=0;
    let tagToDel:Tag;
    this.itemproyect.proyecttags.forEach(element => {
      if (element.tagname===tag){
        ret=i;
      }
      i++;
    });
    if (ret > -1) {
      tagToDel=this.itemproyect.proyecttags.splice(ret, 1)[0];
      if (this.itemproyect.id>0){
        this.formDataTag=new FormData();
        this.formDataTag.append("proyectid",this.itemproyect.id.toString());
        this.formDataTag.append("tagname",JSON.parse(JSON.stringify(tagToDel.tagname)));
        this.http.post(environment.URL+"/tagToDelete",this.formDataTag).subscribe();          
      }
    }
  }

  addTag(element:string){
    let newTag:Tag;
    if (element!=""){
        this.formDataTag=new FormData();
        this.formDataTag.append("tagname",element);
        this.http.post<Tag>(environment.URL+"/addTag",this.formDataTag).subscribe(data=>{
            this.tagAvailable.push(data);
            this.addTagToProyect(data.tagname);
          }
        );         
    }
    this.tagCheck="";
  }
}
