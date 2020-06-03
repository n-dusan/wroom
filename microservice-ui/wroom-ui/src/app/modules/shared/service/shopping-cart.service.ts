import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { ShoppingCartItem } from '../models/shopping-cart-item.model';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  shoppingCartSubject: BehaviorSubject<ShoppingCartItem[]> = new BehaviorSubject([]);
  shoppingCartData = this.shoppingCartSubject.asObservable();


  constructor() { }

  updateShoppingCartData(data) {
    this.shoppingCartSubject.next(data);
  }

  addToCart(item: ShoppingCartItem) {
    const currentValue = this.shoppingCartSubject.value;

    // Check if that item already exists in the cart
    const same = currentValue.find(obj => {return obj?.adId === item.adId});
    if(same) {
      if(same.from === item.from && same.to === item.to) {
        return;
      }
    }
    const updatedValue = [...currentValue, item];
    this.shoppingCartSubject.next(updatedValue);
    console.log('updated', updatedValue)
  }

  getShoppingCart() {
    return this.shoppingCartSubject.value;
  }

  getShoppingCartAsObservable() {
    return this.shoppingCartSubject.asObservable();
  }
}
