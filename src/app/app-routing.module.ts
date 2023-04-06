import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginSignupComponent } from './login-signup/login-signup.component'
import { HomepageComponent } from './homepage/homepage.component';


const routes: Routes = [
   { path: '', redirectTo: 'Login', pathMatch: 'full' },
   {  path:'Login', component:LoginSignupComponent},
  {  path: 'homepage', component: HomepageComponent }
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
