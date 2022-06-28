import {Component, OnInit} from '@angular/core';
import {Charity, ProfileInfo, Transaction} from '../interfaces/interfaces';
import {HttpService} from '../services/http.service';
import {AuthService} from '../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-my-profile-page',
  templateUrl: './my-profile-page.component.html',
  styleUrls: ['./my-profile-page.component.css']
})
export class MyProfilePageComponent implements OnInit {
  profileInfo: ProfileInfo;
  charitiesVolunteer: Charity[];
  charitiesDonates: Charity[];
  charitiesOwned: Charity[];
  charitiesInProcess: Charity[];
  charitiesCompleted: Charity[];
  transactions: Transaction[];
  ownedActive = true;
  donatesActive = false;
  volunteerActive = false;
  activitiesActive = false;
  inProcessActive = false;
  completedActive = false;

  constructor(private service: HttpService, public auth: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.getAllInfoAboutCustomer();
  }

  getAllInfoAboutCustomer() {
    this.service.customerInfo().subscribe((response) => {
      this.profileInfo = response;
      this.getVolunteers();
      this.getDonates();
      this.getOwned();
      this.getTransactions();
      this.getCharitiesInProcess();
      this.getCharitiesCompleted();
    });
  }

  getVolunteers() {
    this.service.getCharitiesCurrentCustomerIsVolunteer().subscribe((response) => {
      if (response.length > 0) {
        this.charitiesVolunteer = response;
      }
    });
  }

  getDonates() {
    this.service.getCharitiesCurrentCustomerDonater().subscribe((response) => {
      if (response.length > 0) {
        this.charitiesDonates = response;
      }
    });
  }

  getOwned() {
    this.service.getCharitiesCurrentCustomerOwner().subscribe((response) => {
      if (response.length > 0) {
        this.charitiesOwned = response;
      }
    });
  }

  getTransactions() {
    this.service.getTransactions().subscribe((response) => {
      if (response.length > 0) {
        this.transactions = response;
      }
    });
  }

  getCharitiesInProcess() {
    this.service.getCurrentCustomerCharitiesByStatus('IN_PROCESS').subscribe((response) => {
      if (response.length > 0) {
        this.charitiesInProcess = response;
      }
    });
  }

  getCharitiesCompleted() {
    this.service.getCurrentCustomerCharitiesByStatus('COMPLETED').subscribe((response) => {
      if (response.length > 0) {
        this.charitiesCompleted = response;
      }
    });
  }
}
