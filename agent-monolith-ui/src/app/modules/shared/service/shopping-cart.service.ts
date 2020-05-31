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
    console.log('Adding...', item)
    const currentValue = this.shoppingCartSubject.value;
    const updatedValue = [...currentValue, item];
    this.shoppingCartSubject.next(updatedValue);
  }

  getShoppingCart() {
    console.log(this.shoppingCartSubject.value);
    return this.shoppingCartSubject.value;
  }

  getShoppingCartAsObservable() {
    return this.shoppingCartSubject.asObservable();
  }
}
