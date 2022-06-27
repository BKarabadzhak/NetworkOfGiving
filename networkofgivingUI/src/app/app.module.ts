import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClarityModule } from '@clr/angular';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RegistrationComponent } from './registration/registration.component';
import {HttpClientModule} from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { CharitiesComponent } from './charities/charities.component';
import { CharityComponent } from './charities/charity/charity.component';
import {CharityDetailsComponent} from './charities/charity-details/charity-details.component';
import {SearchPipe} from './services/pipes/search.pipe';
import { MyProfilePageComponent } from './my-profile-page/my-profile-page.component';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    CharitiesComponent,
    CharityComponent,
    CharityDetailsComponent,
    SearchPipe,
    MyProfilePageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ClarityModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
