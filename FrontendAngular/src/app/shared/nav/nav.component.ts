import { AuthService } from './../../services/auth.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent {
userLoginOn:boolean=false;
constructor(private authService:AuthService){}
ngOnInit():void{
    //Obtención de estado de login
  this.authService.currentUserLoginOn.subscribe(
    {
      next:(userLoginOn)=>{
        this.userLoginOn=userLoginOn;
      }
    }
  )
}
logout(){
  this.authService.logout();
}

}
