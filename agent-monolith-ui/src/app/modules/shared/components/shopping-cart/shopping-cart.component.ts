import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../../service/shopping-cart.service';
import { Ad } from '../../models/ad.model';
import { ShoppingCartItem } from '../../models/shopping-cart-item.model';
import { AdService } from '../../service/ads.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  ads: Ad[] = [];
  cartItems: ShoppingCartItem[] = [];

  constructor(private shoppingCartService: ShoppingCartService,
    private adService: AdService,
    private toastr: ToastrService) { }

  ngOnInit(): void {

    this.shoppingCartService.getShoppingCartAsObservable().subscribe(
      data => {
        this.cartItems = data;
        this.fetchAds();
      }
    );

  }

  fetchAds() {
    for(let item of this.cartItems) {

      this.adService.get(item.adId).subscribe(
        data => {
          this.ads.push(data);
        },
        error => {
          this.toastr.error('An unexpected error occurred.', 'Error')
        }
      );
    }
  }

}
