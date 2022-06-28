import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {
  Charity,
  Customer,
  CustomerInfo,
  Donate,
  LoginRequest,
  LoginResponse,
  MessageResponse,
  ProfileInfo,
  RegisterResponse,
  Transaction
} from '../interfaces/interfaces';
import {catchError} from 'rxjs/operators';


@Injectable({providedIn: 'root'})
export class HttpService {
  url = 'http://54.174.196.3:8080/api';

  constructor(private http: HttpClient) {
  }

  registerCustomer(customer: Customer): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${this.url}/auth/register`, customer)
      .pipe(catchError(error => {
        return throwError(error);
      }));
  }

  loginCustomer(customer: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.url}/auth/login`, customer)
      .pipe(catchError(error => {
        return throwError(error);
      }));
  }


  getCharities(): Observable<Charity[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.get<Charity[]>(`${this.url}/charities`, {headers});
  }

  getAllCharitiesByStatus(status: string): Observable<Charity[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.get<Charity[]>(`${this.url}/charities/${status}`, {headers});
  }

  getCurrentCustomerCharitiesByStatus(status: string): Observable<Charity[]> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));

    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`,
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<Charity[]>(`${this.url}/charities/currentUser/${status}`, httpOptions);
  }

  getCharity(id: number): Observable<Charity> {
    const url = `${this.url}/charities/get/${id}`;
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

  recommendedAmountOfDonate(charityId: number): Observable<number> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`
      })
    };

    const url = `${this.url}/charities/recommendedAmount/${charityId}`;
    return this.http.get<number>(url, httpOptions);
  }

  customerInfo(): Observable<ProfileInfo> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`
      })
    };

    const url = `${this.url}/user/customerInfo`;
    return this.http.get<ProfileInfo>(url, httpOptions);
  }

  getCharitiesCurrentCustomerIsVolunteer(): Observable<Charity[]> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`,
        'Content-Type': 'application/json'
      })
    };

    const url = `${this.url}/user/charitiesTakePartVolunteer`;
    return this.http.get<Charity[]>(url, httpOptions);
  }

  getCharitiesCurrentCustomerDonater(): Observable<Charity[]> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`,
        'Content-Type': 'application/json'
      })
    };

    const url = `${this.url}/user/charitiesDonater`;
    return this.http.get<Charity[]>(url, httpOptions);
  }

  getCharitiesCurrentCustomerOwner(): Observable<Charity[]> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`,
        'Content-Type': 'application/json'
      })
    };

    const url = `${this.url}/user/charities`;
    return this.http.get<Charity[]>(url, httpOptions);
  }

  getTransactions(): Observable<Transaction[]> {
    const info: CustomerInfo = JSON.parse(localStorage.getItem('currentUser'));
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${info.token}`,
        'Content-Type': 'application/json'
      })
    };

    const url = `${this.url}/user/transactions`;
    return this.http.get<Transaction[]>(url, httpOptions);
  }
}
