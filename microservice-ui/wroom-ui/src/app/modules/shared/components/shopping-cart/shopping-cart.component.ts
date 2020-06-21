import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../../service/shopping-cart.service';
import { Ad } from '../../models/ad.model';
import { ShoppingCartItem } from '../../models/shopping-cart-item.model';
import { ToastrService } from 'ngx-toastr';
import { DomSanitizer } from '@angular/platform-browser';
import { OwnerAds } from '../../models/owner-ads.model';
import { MatDialog } from '@angular/material/dialog';
import { CreateBundleDialogComponent } from '../create-bundle-dialog/create-bundle-dialog.component';
import { RentRequest } from '../../models/rent-request.model';
import { SearchService } from '../../service/search.service';
import { AuthService } from 'src/app/modules/auth/service/auth.service';
import { LoggedUser } from 'src/app/modules/auth/model/logged-user.model';

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
    private toastr: ToastrService,
    public sanitizer: DomSanitizer,
    private dialog: MatDialog,
    private searchService: SearchService,
    private authService: AuthService) { }

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

  // Get all ads that are in shopping cart from backend
  fetchAds() {
    for (let item of this.cartItems) {

      this.searchService.getAd(item.adId).subscribe(
        data => {
          const ad: Ad = data;

          this.searchService.getVehicle(data.vehicleId).subscribe(
            dataVehicle => {
              ad.vehicleObj = dataVehicle
              this.ads.push(ad);
              console.log('added an ad with vehicle obj', ad)


              // Location
              this.searchService.getLocation(ad.locationId).subscribe(
                data => {
                  ad.locationObj = data;

                  this.searchService.getVehicleImages(ad?.vehicleObj?.id).subscribe(
                    data => {
                      console.log(data)
                      if(data.length > 0) {
                        ad.image = "data:image/jpeg;base64," + data[0];
                      }


                      this.searchService.getOwner(ad.id).subscribe(
                        data => {

                          // populate owners
                          var owner = this.owners.find(obj => { return obj?.ownerId === ad?.vehicleObj?.ownerId });
                          if (owner) {  //ako postoji owner tog ad-a
                            this.owners.find(obj => { return obj.ownerId === ad?.vehicleObj?.ownerId }).ads.push(ad?.id);
                            this.owners.find(obj => { return obj.ownerId === ad?.vehicleObj?.ownerId }).adsObj?.push(ad);
                          }
                          else {  // ako ne postoji taj owner
                            this.owners.push(new OwnerAds(ad.vehicleObj?.ownerId, [ad.id], data.email, [ad]));
                          }

                          this.loaded = true;

                        },
                        error => {
                          this.loaded = true;
                          this.toastr.error('There was an error!', 'Owner')
                        }
                      );

                    },
                    error => {
                      console.log(error)
                      this.loaded = true;
                      this.toastr.error('There was an error!', 'Image')
                    }
                  );



                },
                error => {
                  this.loaded = true;
                  this.toastr.error('There was an error!', 'Location')
                }
              );

            },
            error => {
              this.loaded = true;
              this.toastr.error('There was an error!', 'Vehicles')
            }
          );
        },
        error => {
          this.loaded = true;
          this.toastr.error('An unexpected error occurred.', 'Error')
        }
      );

    }
  }

  public getSantizeUrl(url: any) {
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }

  sendRequest(owner: OwnerAds) {
    var requests: RentRequest[] = [];
    this.authService.whoami().subscribe( (user: LoggedUser) => {
      for (let ad of owner.adsObj) {
        const from = this.cartItems.find(obj => { return obj.adId === ad.id })?.from;
        const to = this.cartItems.find(obj => { return obj.adId === ad.id })?.to;
        requests.push(new RentRequest(null, null, from, to, user.id, ad, null, false, null));
      }

      let dialogRef = this.dialog.open(CreateBundleDialogComponent,
        {
          data: requests
        }
      );

      dialogRef.afterClosed().subscribe(
        data => {
          // TODO: Remove from cart requests that are sent
        }
      )
    })


  }

}
