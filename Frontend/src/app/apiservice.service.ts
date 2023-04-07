import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiserviceService {

  private baseUrl = "hello.com"

  constructor(private Http: HttpClient) { }

  get (endpoint: string): Observable<any> {


   return this.Http.get(endpoint);
  }

  post (endpoint: string, body: any): Observable<any> {
    console.log(body)
    return this.Http.post(endpoint, body);
  }
}

