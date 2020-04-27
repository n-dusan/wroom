import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend-agent';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    console.log('Greetings! Attempting to establish http communication with monolith back-end...');
    this.http.get('http://localhost:8080/api/stub/test').subscribe(response => {
      console.log('Received a response: ', response);

      console.log('Testing POST');
      this.http.post('http://localhost:8080/api/stub/test', { test: "aaa"}).subscribe(response => {
      console.log('POST response: ', response)
      })
    })
  }
}
