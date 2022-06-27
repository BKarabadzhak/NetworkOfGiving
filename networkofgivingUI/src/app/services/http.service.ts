import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {
  Charity,
  Customer,
  CustomerInfo,
  Donate,
  LoginRequest,
  LoginResponse,
  MessageResponse,
  RegisterResponse
} from '../interfaces/interfaces';
import {catchError, map} from 'rxjs/operators';


@Injectable({providedIn: 'root'})
export class HttpService {
  url = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  registerCustomer(customer: Customer): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${this.url}/auth/register`, customer)
      .pipe(catchError(error => {
        console.log(error.message);
        return throwError(error);
    }));
  }

  loginCustomer(customer: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.url}/auth/login`, customer)
      .pipe(catchError(error => {
        //console.log(error.message);
        return throwError(error);
      }));
  }

  loginCustomerExampleWithHEADERS(customer: LoginRequest): Observable<LoginResponse> {
    const headerss = new HttpHeaders({
      'MyCustomHeader': Math.random().toString(),
      'sdfsd': 'sdfs'
    });

    return this.http.post<LoginResponse>(`${this.url}/auth/login`, customer, {
      headers: headerss
    })
      .pipe(catchError(error => {
        console.log(error.message);
        return throwError(error);
      }));
  }

  loginCustomerExampleWithQUERYPARAMS(customer: LoginRequest): Observable<LoginResponse> {
    let paramss = new HttpParams();
    paramss = paramss.append('username', 'bohdan');
    paramss = paramss.append('id', '15');

    // Эквивалент http://localhost:8080/api/auth/login?username=bohdan&id=15
    return this.http.post<LoginResponse>(`${this.url}/auth/login`, customer, {
      params: paramss
    })
      .pipe(catchError(error => {
        console.log(error.message);
        return throwError(error);
      }));
  }

  loginCustomerExampleWithFULLRESPONSE(customer: LoginRequest): Observable<LoginResponse> {
    let paramss = new HttpParams();
    paramss = paramss.append('username', 'bohdan');
    paramss = paramss.append('id', '15');

    // Эквивалент http://localhost:8080/api/auth/login?username=bohdan&id=15
    return this.http.post<LoginResponse>(`${this.url}/auth/login`, customer, {
      params: paramss,
      observe: 'response'
    })
      .pipe(map(response => {
        //Получаю более развёрнутый ответ
          console.log(response);
          return response.body;
      }),
        catchError(error => {
        console.log(error.message);
        return throwError(error);
      }));
  }

  getCharities(): Observable<Charity[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.get<Charity[]>(`${this.url}/charities`, {headers});
  }

  getCharity(id: number): Observable<Charity> {
    const url = `${this.url}/charities/${id}`;
    return this.http.get<Charity>(url);
  }

  addCharity(data: string, file: File): Observable<MessageResponse> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));

    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`
      })
    };

    const formData = new FormData();
    formData.append('file', file);
    formData.append('object', data);
    const url = `${this.url}/charities/add`;
    return this.http.post<MessageResponse>(url, formData, httpOptions);
  }

  ownedByCurrentCustomer(charityId: number): Observable<boolean> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`
      })
    };

    const url = `${this.url}/user/charity/${charityId}`;
    return this.http.get<boolean>(url, httpOptions);
  }

  currentCustomerVolunteerCharityById(charityId: number): Observable<boolean> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`
      })
    };

    const url = `${this.url}/user/isVolunteer/${charityId}`;
    return this.http.get<boolean>(url, httpOptions);
  }

  donate(donate: Donate): Observable<MessageResponse> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`
      })
    };

    const url = `${this.url}/user/donate`;
    return this.http.post<MessageResponse>(url, donate, httpOptions);
  }

  volunteer(charityId: number): Observable<MessageResponse> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`
      })
    };

    const url = `${this.url}/user/volunteer/${charityId}`;
    return this.http.get<MessageResponse>(url, httpOptions);
  }

  delete(charityId: number): Observable<MessageResponse> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`
      })
    };

    const url = `${this.url}/charities/${charityId}`;
    return this.http.delete<MessageResponse>(url, httpOptions);
  }

}
