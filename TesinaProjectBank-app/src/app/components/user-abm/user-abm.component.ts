import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-abm',
  templateUrl: './user-abm.component.html',
  styleUrls: ['./user-abm.component.css']
})
export class UserAbmComponent implements OnInit {

  usersList:User[]=[];

  constructor(
    private userService:UserService,
    private router:Router,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(data=>{
      this.usersList=data;
    });
  }
  editUser(user:User){
    this.router.navigate(["/usersView",{'item':JSON.stringify(user)}]);
  }
  deleteUser(user:User){
    this.userService.deleteUser(user).subscribe();
    this.router.navigate(['/users']).then(()=>{window.location.reload();});
  }

  open(content: any, user:User) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result:any) => {
      this.onApply(user);
    }, (reason:any) => {
      
    });
  }
  onApply(user:User){
    this.userService.deleteUser(user).subscribe();
    this.router.navigate(['/users']).then(()=>{window.location.reload();});
  }

}
