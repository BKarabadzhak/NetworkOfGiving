import {Component, Input, OnInit} from '@angular/core';
import {Charity} from '../../interfaces/interfaces';
import {Router} from '@angular/router';

@Component({
  selector: 'app-charity',
  templateUrl: './charity.component.html',
  styleUrls: ['./charity.component.css']
})
export class CharityComponent implements OnInit {

  @Input()
  charity: Charity;

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  goToDetailsPage() {
    if (this.router.url === '/charities') {
      this.router.navigate(['/charities/' + this.charity.id], {
        queryParams: {
          toHomePage: 'homePage'
        }
      });
    } else {
      this.router.navigate(['/charities/' + this.charity.id], {
        queryParams: {
          toProfilePage: 'profilePage'
        }
      });
    }
  }
}
