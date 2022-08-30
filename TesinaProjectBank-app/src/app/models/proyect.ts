import { ProyectDoc } from "./proyectdoc";
import { Tag } from "./tag";
import { Userdata } from "./userdata";

export class Proyect{
    id: number=0;
    title: string='';
    userdata: Userdata = new Userdata();
    description:string='';
    name:string='';
    lastname:string='';
    contact_mail:string='';
    date_generation: Date= new Date;
    overdue_proposal:boolean=false;
    deleted:boolean= false;
    last_date_actualization: Date= new Date;
    proyectdoc: ProyectDoc[] = [];
    proyecttags: Tag[] = [];
}