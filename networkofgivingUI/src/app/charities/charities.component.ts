import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpService} from '../services/http.service';
import {Charity} from '../interfaces/interfaces';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-charities',
  templateUrl: './charities.component.html',
  styleUrls: ['./charities.component.css']
})
export class CharitiesComponent implements OnInit {

  constructor(private route: ActivatedRoute, private httpService: HttpService, private http: HttpClient, private router: Router, public auth: AuthService) {
  }

  charities: Charity[];
  searchString = '';
  showModalClause = false;
  form: FormGroup;
  file: File;
  fileIsCorrect;
  fileIsSet = false;
  submitted = false;
  responseText = '';
  alertIsClosed = true;
  isDonationAndParticipantsUnset = false;

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.responseText = this.route.snapshot.queryParamMap.get('deleted');
      if (this.responseText) {
        this.alertIsClosed = false;
        this.router.navigate(['/charities']);
      }
    });

    this.getCharities();

    this.fileIsCorrect = true;

    this.form = new FormGroup({
      title: new FormControl('', [Validators.required, Validators.minLength(6),
        Validators.maxLength(95)], [this.uniqTitle.bind(this)]),
      description: new FormControl('', [Validators.required, Validators.maxLength(250)]),
      donationRequired: new FormControl(),
      participantsRequired: new FormControl()
    });
  }

  getCharities() {
    this.charities = null;
    this.httpService.getCharities().subscribe((charities: Charity[]) => {
      this.charities = charities;
    });
  }

  uniqTitle(control: FormControl): Observable<{ [key: string]: boolean }> {
    return this.http.post<boolean>('http://localhost:8080/api/charities/exist', control.value).pipe(map((response) => {
      if (response) {
        return {uniqTitle: true};
      } else {
        return null;
      }
    }), catchError(err => {
      //console.log('Error with checking', err.message);
      return throwError(err);
    }));
  }

  openModalAddCharity() {
    this.showModalClause = true;
  }

  setFile(file: File): void {
    if (file.type.match('image/*') || file == null) {
      this.file = file;
      this.fileIsCorrect = true;
      this.fileIsSet = true;
    } else {
      this.fileIsCorrect = false;
    }
  }

  submit() {
    this.submitted = true;
    const formData = {...this.form.value};

    if (!formData.donationRequired && !formData.participantsRequired) {
      this.isDonationAndParticipantsUnset = true;
      this.submitted = false;
    } else {
      const data: string = JSON.stringify(formData);

      this.httpService.addCharity(data, this.file).subscribe(
        (response) => {
          this.isDonationAndParticipantsUnset = false;
          this.showModalClause = false;
          this.getCharities();
          this.responseText = response.message;
          this.alertIsClosed = false;
          this.submitted = false;
          this.form.reset();
        }
      );
    }
  }

  close() {
    this.showModalClause = false;
    this.form.reset();
  }

}

