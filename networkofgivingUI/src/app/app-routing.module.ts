import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CharitiesComponent} from './charities/charities.component';
import {RegistrationComponent} from './registration/registration.component';
import {LoginComponent} from './login/login.component';
import {CharityDetailsComponent} from './charities/charity-details/charity-details.component';
import {AuthGuard} from './auth.guard';

// http://localhost:4200/charities - Home compoonent -CharitiesComponent
// http://localhost:4200/registration - RegistrationComponent
// http://localhost:4200/login - LoginComponent
const routes: Routes = [
  //{ path: '', redirectTo: '/charities', pathMatch: 'full' },
  //, canActivate: [AuthGuard]
  {path: 'charities', component: CharitiesComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'login', component: LoginComponent},
  {path: 'charities/:id', component: CharityDetailsComponent},
  { path: '**', redirectTo: '/charities', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
