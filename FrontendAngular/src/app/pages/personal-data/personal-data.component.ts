import { AuthService } from './../../services/auth.service';
import { HeaderComponent } from './../../shared/header/header.component';
import { Component } from '@angular/core';
import { User } from 'src/app/services/dto/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-personal-data',
  templateUrl: './personal-data.component.html',
  styleUrls: ['./personal-data.component.css']
})
export class PersonalDataComponent {
errorMessage:string ="";
username?:any;
userLoginOn:boolean=false;
user?:User;

//Obtención de datos del usuario
constructor(private userService: UserService,private authService:AuthService){
  this.username=localStorage.getItem("username");
  //Dtos de usuario
this.userService.getUser(this.username).subscribe({
  next:(userData)=>{
    this.user=userData;
  },
complete:()=>{
  console.log("Datos de usuario obtenidos")
}
})
//estado de login
this.authService.currentUserLoginOn.subscribe({
  next:(userLoginOn)=>{
    this.userLoginOn=userLoginOn;
  }
})
}


}
