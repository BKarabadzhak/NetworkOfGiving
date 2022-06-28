import {Component, Input, OnInit} from '@angular/core';
import {Transaction} from '../interfaces/interfaces';
import {Router} from '@angular/router';

@Component({
  selector: 'app-activity',
  templateUrl: './activity.component.html',
  styleUrls: ['./activity.component.css']
})
export class ActivityComponent implements OnInit {

  @Input()
  transaction: Transaction;

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  goToDetailsPage() {

    this.router.navigate(['/charities/' + this.transaction.charity.id], {
      queryParams: {
        toHomePage: 'profilePage'
      }
    });

  }
}
