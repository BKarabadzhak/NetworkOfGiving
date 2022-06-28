import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpService} from '../services/http.service';
import {LoginRequest} from '../interfaces/interfaces';
import {Router} from '@angular/router';
import {AuthService} from '../services/auth.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  isError = false;
  submitted = false;

  constructor(private service: HttpService, private router: Router, public authService: AuthService) {
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]),
      password: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(20)])
    });
  }

  login() {

    const formData: LoginRequest = {...this.form.value};
    this.submitted = true;

    this.authService.login(formData)
      .subscribe((response) => {
          this.isError = false;
          this.router.navigate(['/charities']);
          this.submitted = false;
        },
        (error) => {
          this.isError = true;
          this.submitted = false;
        });

  }
}
