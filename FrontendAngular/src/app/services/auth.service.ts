import { PersonalDataComponent } from './../pages/personal-data/personal-data.component';
import { Injectable } from '@angular/core';
import { LoginRequest } from './dto/loginRequest';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import {
  Observable,
  catchError,
  throwError,
  BehaviorSubject,
  tap,
  map,
} from 'rxjs';
import { ResponseLogin } from './dto/response-login';
import { enviroment } from 'src/enviroment/enviroment';
import { User } from './dto/user';
@Injectable({
  providedIn: 'root',
})
export class AuthService {

  constructor(private http: HttpClient ) {
    this.currentUserLoginOn = new BehaviorSubject<boolean>(
      sessionStorage.getItem('token') != null
    );
    this.currentUserData = new BehaviorSubject<String>(
      sessionStorage.getItem('token') || ''
    );
  }
  //Estado de login de usuario
  currentUserLoginOn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  //Response de login
  currentUserData: BehaviorSubject<String> = new BehaviorSubject<String>('');
  //Método para el inicio de sesión
  login(credentials:any):Observable<any>{
    return this.http.post<any>(enviroment.urlApi+"/auth/login",credentials).
  pipe(
      tap( (data) => {
        localStorage.setItem("token", data.token);
        this.currentUserData.next(data.token);
        this.currentUserLoginOn.next(true);
        //Se utiliza para obtener los datos del usuario y mostrarlos
        localStorage.setItem("username", credentials.username);
      })
    );
  }

  //Método para cerrar
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    this.currentUserLoginOn.next(false);
  }

  //Getters
  get userData(): Observable<String> {
    return this.currentUserData.asObservable();
  }

  get userLoginOn(): Observable<boolean> {
    return this.currentUserLoginOn.asObservable();
  }

}
