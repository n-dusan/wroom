import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { environment } from '../environments/environment'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'wroom-ui';

  searchUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + environment.searchService
  vehicleUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + environment.vehicleService

  constructor(private http: HttpClient) { }

  testZuul() {
    const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');
    console.log('Talking to ' + this.searchUrl + '/hello')
    this.http.get<string>(this.searchUrl + '/hello', { responseType: 'text' as 'json' }).subscribe(response => {
      console.log('response from ' + this.searchUrl + '/hello -> ' + response)
    })

    console.log('Talking to ' + this.vehicleUrl + '/hello')
    this.http.get<String>(this.vehicleUrl + '/hello', { responseType: 'text' as 'json' }).subscribe(response => {
      console.log('response from ' + this.vehicleUrl + '/hello -> ' + response)
    })
  }

}
