import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../../service/shopping-cart.service';
import { Ad } from '../../models/ad.model';
import { ShoppingCartItem } from '../../models/shopping-cart-item.model';
import { AdService } from '../../service/ads.service';
import { ToastrService } from 'ngx-toastr';
import { DomSanitizer } from '@angular/platform-browser';
import { VehicleService } from '../../service/vehicle.service';
import { OwnerAds } from '../../models/owner-ads.model';
import { Vehicle } from '../../models/vehicle.model';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  ads: Ad[] = [];
  cartItems: ShoppingCartItem[] = [];
  owners: OwnerAds[] = [];  // Group all ads from one owner

  loaded: boolean = false;
  empty: boolean = true;

  constructor(private shoppingCartService: ShoppingCartService,
    private adService: AdService,
    private toastr: ToastrService,
    public sanitizer: DomSanitizer,
    public vehicleService: VehicleService) { }

  ngOnInit(): void {
    this.loaded = false;

    this.shoppingCartService.getShoppingCartAsObservable().subscribe(
      data => {
        this.cartItems = data;
        console.log('cart items', data)

        if (this.cartItems.length == 0) {
          this.loaded = true;
          this.empty = true
        }
        else {
          this.empty = false;
          this.fetchAds();
        }
      }
    );
  }

  // Get all ads that are in shopping cart from server
  fetchAds() {
    for (let item of this.cartItems) {

      this.adService.get(item.adId).subscribe(
        data => {
          const ad: Ad = data;

          this.vehicleService.get(data.vehicleId).subscribe(
            dataVehicle => {
              ad.vehicleObj = dataVehicle
              this.ads.push(ad);
              console.log('added an ad with vehicle obj', ad)

              // populate owners
              var owner = this.owners.find(obj => { return obj?.ownerId === ad?.vehicleObj?.ownerId });
              if (owner) {  //ako postoji owner tog ad-a
                  this.owners.find(obj => { return obj.ownerId === ad?.vehicleObj?.ownerId }).ads.push(ad?.id);
                  this.owners.find(obj => { return obj.ownerId === ad?.vehicleObj?.ownerId }).adsObj?.push(ad);
              }
              else {  // ako ne postoji taj owner
                this.owners.push(new OwnerAds(ad.vehicleObj?.ownerId, [ad.id], "", [ad]));
              }

              // Location
              this.adService.getLocation(ad.locationId).subscribe(
                data => {
                  ad.locationObj = data;
                  this.loaded = true;
                },
                error => {
                  this.loaded = true;
                  this.toastr.error('There was an error!', 'Location')
                }
              );

            },
            error => {
              this.toastr.error('There was an error!', 'Vehicles')
            }
          );
        },
        error => {
          this.toastr.error('An unexpected error occurred.', 'Error')
        }
      );

    }
  }

  public getSantizeUrl(url: any) {
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }
}
