import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {HttpService} from '../../services/http.service';
import {Charity, Donate} from '../../interfaces/interfaces';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-charity-details',
  templateUrl: './charity-details.component.html',
  styleUrls: ['./charity-details.component.css']
})
export class CharityDetailsComponent implements OnInit {
  charity: Charity;
  charityOwnCurrentCustomer;
  donateModalIsOpen = false;
  donationAmount: number;
  volunteerModalIsOpen = false;
  deleteModalIsOpen = false;
  checkOwner = true;
  checkIfVolunteer = false;
  submitted = false;
  responseMessage: string;
  alertIsClosed = true;
  amountOfDonate: number;
  toHomePage: string;
  toProfilePage: string;
  backRoute: string;

  constructor(private route: ActivatedRoute, private service: HttpService, public auth: AuthService, private router: Router) {
  }


  ngOnInit(): void {
    this.getParamsFromUrl();

    this.route.queryParams.subscribe((params: Params) => {
      this.toHomePage = params.toHomePage;
      this.toProfilePage = params.toProfilePage;
      if (this.toHomePage) {
        this.backRoute = this.toHomePage;
      } else if (this.toProfilePage) {
        this.backRoute = this.toProfilePage;
      }
    });
  }

  checkIfCurrentUserOwnThisCharity() {
    if (this.charity) {
      this.service.ownedByCurrentCustomer(this.charity.id).subscribe(
        (response) => {
          if (response) {
            this.charityOwnCurrentCustomer = true;
          } else {
            this.charityOwnCurrentCustomer = false;
          }
        }
      );
    }
  }

  checkIfCurrentUserVolunteerThisCharity() {
    if (this.charity) {
      this.service.currentCustomerVolunteerCharityById(this.charity.id).subscribe(
        (response) => {
          if (response) {
            this.checkIfVolunteer = true;
          } else {
            this.checkIfVolunteer = false;
          }
        });
    }
  }

  checkAmountForDonate() {
    if (this.charity) {
      this.service.recommendedAmountOfDonate(this.charity.id).subscribe((response) => {
        this.amountOfDonate = response;
      });
    }
  }

  getParamsFromUrl() {
    this.route.params.subscribe((params: Params) => {

      this.service.getCharity(+params.id)
        .subscribe((charity) => {
            this.charity = charity;
            if (this.auth.isAuth && this.checkOwner) {
              this.checkIfCurrentUserOwnThisCharity();
            }
            if (this.auth.isAuth && !this.checkIfVolunteer) {
              this.checkIfCurrentUserVolunteerThisCharity();
            }
            if (this.auth.isAuth && this.charity.donationRequired) {
              this.checkAmountForDonate();
            }
          },
          (error) => {
          });
    });
  }

  donate() {
    this.submitted = true;
    const donate: Donate = {
      id: this.charity.id,
      amount: this.donationAmount
    };

    this.service.donate(donate).subscribe((response) => {
      this.checkOwner = false;
      this.donateModalIsOpen = false;
      this.alertIsClosed = false;
      this.submitted = false;
      this.responseMessage = response.message;
      this.getParamsFromUrl();
    });
  }

  volunteer() {
    this.submitted = true;
    this.service.volunteer(this.charity.id).subscribe((response) => {
      this.checkOwner = false;
      this.volunteerModalIsOpen = false;
      this.checkIfVolunteer = true;
      this.alertIsClosed = false;
      this.submitted = false;
      this.responseMessage = response.message;
      this.getParamsFromUrl();
    });
  }

  delete() {
    this.submitted = true;
    this.service.delete(this.charity.id).subscribe((response) => {
      this.submitted = false;
      this.router.navigate(['/charities'],
        {
          queryParams: {
            deleted: response.message
          }
        });
    });
  }

  backDecision() {
    if (this.backRoute === 'profilePage') {
      this.router.navigate(['/profile']);
    } else {
      this.router.navigate(['/charities']);
    }
  }
}
