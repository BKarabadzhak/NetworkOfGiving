import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Observable, throwError} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {catchError, map} from 'rxjs/operators';
import {HttpService} from '../services/http.service';
import {Customer} from '../interfaces/interfaces';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  form: FormGroup;
  private usernameIsValid: boolean;
  serverIsDisabled = false;
  submitted = false;

  constructor(private http: HttpClient, private service: HttpService, private router: Router) {
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      username: new FormControl('', [
          Validators.maxLength(20), Validators.required, Validators.minLength(6)],
        [this.uniqUsername.bind(this)]),
      password: new FormControl('',
        [Validators.required, Validators.minLength(6), Validators.maxLength(20)]),
      name: new FormControl('',
        [Validators.required, Validators.minLength(6), Validators.maxLength(40)]),
      age: new FormControl('',
        [Validators.min(0), Validators.max(100), Validators.minLength(1), Validators.maxLength(3)]),
      gender: new FormControl('',
        [Validators.minLength(1), Validators.maxLength(40)]),
      location: new FormControl('',
        [Validators.minLength(1), Validators.maxLength(40)])
    });
  }

  uniqUsername(control: FormControl): Observable<{ [key: string]: boolean }> {
    return this.http.get<boolean>(`http://localhost:8080/api/user/customer/${control.value}`).pipe(map((response) => {
      if (response) {
        return {uniqUsername: true};
      } else {
        return null;
      }
    }), catchError(err => {
      this.serverIsDisabled = true;
      //console.log('Error with checking', err.message);
      return throwError(err);
    }));
  }

  submit() {
    this.submitted = true;
    const formData: Customer = {...this.form.value};
    this.service.registerCustomer(formData)
      .subscribe((response) => {
          this.router.navigate(['/charities']);
          this.submitted = false;
        },
        (error) => {
          console.log(error.message);
          this.submitted = false;
        });

    //Get any value from form
    //const username = this.form.get('username').value;
    // Set динамически form input
    /*this.form.patchValue({
      username: 'jopa'
    });*/
  }
}
