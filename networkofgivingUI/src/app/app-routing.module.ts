import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CharitiesComponent} from './charities/charities.component';
import {RegistrationComponent} from './registration/registration.component';
import {LoginComponent} from './login/login.component';
import {CharityDetailsComponent} from './charities/charity-details/charity-details.component';
import {AuthGuard} from './auth.guard';
import {MyProfilePageComponent} from './my-profile-page/my-profile-page.component';
import {ActivityComponent} from './activity/activity.component';

const routes: Routes = [
  {path: 'charities', component: CharitiesComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'login', component: LoginComponent},
  {path: 'charities/:id', component: CharityDetailsComponent},
  {
    path: 'profile', component: MyProfilePageComponent, canActivate: [AuthGuard], children: [
      {path: 'activities', component: ActivityComponent}
    ]
  },
  {path: '**', redirectTo: '/charities', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
