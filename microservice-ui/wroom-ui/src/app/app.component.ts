import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { environment } from '../environments/environment'
import { LoggedUser } from './modules/auth/model/logged-user.model';
import { AuthService } from './modules/auth/service/auth.service';
import { Router } from '@angular/router';
import { ShoppingCartService } from './modules/shared/service/shopping-cart.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'wroom-ui';

  searchUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + environment.searchService
  vehicleUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + environment.vehicleService

  user: LoggedUser;

  cartItemsNum: number = 0;
  loaded: boolean = false;

  constructor(private http: HttpClient,
    private authService: AuthService,
    private router: Router,
    private shoppingCartService: ShoppingCartService) { }


  ngOnInit(): void {

    this.authService.getLoggedUser().subscribe(data => {
      this.user = data;
      if (this.user == null) {
        this.authService.reauthenticate();
        
      }
      this.getShoppingCart();
    }); 

    // this.authService.loggedUserSubject.subscribe(
    //   data => {
    //     this.user = data;
    //     this.getShoppingCart();
    //   }
    // );

    if (this.user == null) {
      this.authService.loggedUserSubject.subscribe(
        data => {
          this.user = data;
        }
      )
    }

    // //send whoami to server to re-authenticate
    // this.authService.whoami().subscribe(
    //   data => {
    //     this.user = data;
    //     console.log(this.user)
    //     this.getShoppingCart();
    //   },
    //   error => {
    //     if (error.status == 401) {
    //       localStorage.removeItem('token');
    //       this.router.navigate(['auth']);
    //     }
    //     this.loaded = true;
    //   }
    // );

  }

  getShoppingCart() {
    this.shoppingCartService.getShoppingCartAsObservable().subscribe(
      data => {
        this.cartItemsNum = data.length;
        this.loaded = true;
      },
      error => {
        this.loaded = true;
        console.log(error);
      }
    )
  }

  logout() {
    this.authService.logout();
    this.user = null;
  }

  shoppingCartClick() {
    this.router.navigateByUrl('/cart');
  }

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

  debtsClick() {
    this.router.navigateByUrl('/debts');
  }

}
