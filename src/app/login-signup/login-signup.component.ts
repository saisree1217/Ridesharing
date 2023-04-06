import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiserviceService } from '../apiservice.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login-signup',
  templateUrl: './login-signup.component.html',
  styleUrls: ['./login-signup.component.css']

})

export class LoginSignupComponent implements OnInit {
  constructor(private router: Router
     , private Apiservice: ApiserviceService
    ) {

  }

  email: string;
  password: string;
  name:string;

  ngOnInit(): void {
    document.querySelector('.img-btn')?.addEventListener('click', () => {
      document.querySelector('.cont')?.classList.toggle('s-signup');
      
     

    });
  }

  geturl(): string {
    return `https://authserver-z742wpzi3q-ue.a.run.app/auth/signUp`;
  }

  onKeyEmail($event: any){
    this.email = $event.target.value;
    console.log(this.email);
  
  }
  onKeyName($event:any){
    this.name=$event.target.value;
    console.log(this.name);
  }

  onKeyPassword($event: any){
    this.password = $event.target.value;
  }

  submit(){
  console.log(this.name+" "+this.email  + " " + this.password);


   var  body = {
      "name" : this.name,
      "emailId": this.email,
      "password": this.password

    }

    this.Apiservice.post(this.geturl(), body).subscribe(data => {
      console.log(data)
    })
    this.router.navigate(["homepage"])
  }
  signup(){
    console.log(this.name+" "+this.email  + " " + this.password);


   var  body = {
      "name" : this.name,
      "emailId": this.email,
      "password": this.password

    }

    this.Apiservice.post(this.geturl(), body).subscribe(data => {
      console.log(data)
    })
    

  }


  
}
