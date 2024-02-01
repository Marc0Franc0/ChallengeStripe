import { User } from 'src/app/services/dto/user';
import { AuthService } from './../../services/auth.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
userLoginOn:boolean = false;
userData?:String;
constructor(private authService:AuthService){}
ngOnInit():void{
  //Obtención de datos de usuario y estado de login
this.authService.currentUserLoginOn.subscribe(
  {
    next:(loginOn)=>{
        this.userLoginOn=loginOn;
    }
  }
)

this.authService.currentUserData.subscribe(
  {
    next:(userData)=>{
        this.userData=userData;
    }
  }
)
}
}
