import {Injectable} from '@angular/core';
import {Charity, Customer, LoginRequest, LoginResponse} from '../interfaces/interfaces';
import {HttpService} from './http.service';
import {Router} from '@angular/router';
import {Observable, throwError} from 'rxjs';
import {catchError, map, tap} from 'rxjs/operators';
import {error} from '@angular/compiler/src/util';
import {HttpErrorResponse} from '@angular/common/http';

@Injectable({providedIn: 'root'})
export class AuthService {

  public isAuth = false;
  public logoutModalIsOpen = false;
  public errorMessage = '';
  public currentCustomerUsername = '';

  constructor(private service: HttpService, private router: Router) {
  }

  login(formData: LoginRequest): Observable<LoginResponse> {
    return this.service.loginCustomer(formData).pipe(tap((response) => {
      localStorage.setItem('currentUser', JSON.stringify({ token: response.token, name: response.userResponse.username, id: response.userResponse.id }));
      this.isAuth = true;
      this.currentCustomerUsername = response.userResponse.username;
    }), catchError(this.handleError.bind(this)));
  }

  logoutConfirm() {
    this.logoutModalIsOpen = true;
  }

  logout() {
    this.logoutModalIsOpen = false;
    this.isAuth = false;
    localStorage.setItem('currentUser', null);
  }

  isAuthenticated(): Promise<boolean> {
    return new Promise<boolean>(resolve => {
      resolve(this.isAuth);
    });
  }

  private handleError(error: HttpErrorResponse) {
    const {message} = error.error;
    this.errorMessage = message;
    return throwError(error);
  }
}
