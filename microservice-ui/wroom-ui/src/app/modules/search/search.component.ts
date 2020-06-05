import { Component, OnInit, ViewChild } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { DomSanitizer } from '@angular/platform-browser';
import { ShoppingCartService } from 'src/app/modules/shared/service/shopping-cart.service';
import { ShoppingCartItem } from 'src/app/modules/shared/models/shopping-cart-item.model';
import { Ad } from './model/ad.model';
import { Vehicle } from './model/vehicle.model';
import { VehicleImage } from './model/vehicle-image.model';
import { AdLocation } from './model/ad-location.model';
import { PriceList } from './model/price-list.model';
import { VehicleFeature } from './model/vehicle-feature.model';
import { AdService } from './service/ad.service';
import { VehicleService } from './service/vehicle.service';
import { PriceListService } from './service/price-list.service';
import { TwoDayValidator } from './validators/two-days.validator';
import { PriceDetailsComponent } from './components/price-details/price-details.component';
import { AdDetailComponent } from './components/ad-detail/ad-detail.component';
import { SearchCriteria } from './model/search-criteria.model';
import { AuthService } from '../auth/service/auth.service';
import { SearchService } from './service/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  ads: Ad[] = [];
  adsAfterSearch: Ad[] = [];
  allAds: Ad[] = [];
  vehicles: Vehicle[] = [];
  images: VehicleImage[] = [];
  locations: AdLocation[] = [];
  priceLists: PriceList[] = [];

  // Filters
  brands: VehicleFeature[] = [];
  models: VehicleFeature[] = [];
  allModels: VehicleFeature[] = [];
  fuels: VehicleFeature[] = [];
  gearboxes: VehicleFeature[] = [];
  bodies: VehicleFeature[] = [];

  localUrl: any[];

  dataSource: MatTableDataSource<Ad>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  basicSearchForm: FormGroup;
  filterForm: FormGroup;
  cdw: boolean = false;
  cdwClean = true;
  sort: any[] = [
    { name: 'Price', icon: 'trending_up', asc: true },
    { name: 'Price', icon: 'trending_down', asc: false },
    { name: 'Rate', icon: 'trending_up', asc: true },
    { name: 'Rate', icon: 'trending_down', asc: false },
    { name: 'Mileage', icon: 'trending_up', asc: true },
    { name: 'Mileage', icon: 'trending_down', asc: false }
  ];
  s: any = null;

  constructor(private adService: AdService,
    private vehicleService: VehicleService,
    private pricelistService: PriceListService,
    private toastr: ToastrService,
    private dialog: MatDialog,
    private formBuilder: FormBuilder,
    public sanitizer: DomSanitizer,
    private shoppingCartService: ShoppingCartService,
    private authService: AuthService,
    private searchService: SearchService) { }

  ngOnInit(): void {

    this.initAds();
    this.initFilters();
    this.initForms();

  }

  initAds() {
    // Fetching all necessary data
    // ADS
    // this.adService.all().subscribe(
    this.searchService.ads().subscribe(       
      data => {
        this.ads = data;                                              // WORKS
        this.allAds = data;
        this.adsAfterSearch = data;
        this.dataSource = new MatTableDataSource(this.allAds); 
      },
      error => {
        this.toastr.error('There was an error!', 'Ads')
      }
    );

    // VEHICLES
    this.searchService.vehicles().subscribe(
      data => {
        this.vehicles = data;                                       // WORKS
        for (let v of this.vehicles) {
          var ad = this.ads.find(obj => { return obj.vehicleId === v.id });
          if (ad) {
            ad.vehicleObj = v;
          }
        }

        // remove ads from current user
        this.authService.getLoggedUser().subscribe(     //works
          data => {
            // console.log('user', data)
            const id = data?.id;
            var notMyAds: Ad[] = [];
            notMyAds = this.ads.filter(obj => { return obj.vehicleObj?.ownerId !== id });

            this.ads = notMyAds;
            this.allAds = notMyAds;
            this.adsAfterSearch = notMyAds;
            this.dataSource = new MatTableDataSource(this.allAds);
          }
        )
      },
      error => {
        this.toastr.error('There was an error!', 'Vehicles')
      }
    );

    // IMAGES
    this.searchService.getVehicleImage().subscribe(   // works
      data => {
        this.images = data;
        for (let img of this.images) {
          if (this.ads.find(obj => { return obj.vehicleId === img.vehicleId })) {
            if (img.image) {
              this.ads.find(obj => { return obj.vehicleId === img.vehicleId }).image = "data:image/jpeg;base64," + img.image;
            }
          }
        }
      },
      error => {
        this.toastr.error('There was an error!', 'Images')
      }
    );


    this.searchService.getLocations().subscribe(
      data => {
        this.locations = data;                          // works
        for (let a of this.ads) {
          a.locationObj = this.locations.find(obj => { return obj.id === a.locationId });
        }
      },
      error => {
        this.toastr.error('There was an error!', 'Locations')
      }
    );

    this.searchService.pricelists().subscribe(
      data => {
        this.priceLists = data;

        for (let a of this.ads) {
          a.priceListObj = this.priceLists.find(obj => { return obj.id === a.priceListId });
        }
      },
      error => {
        this.toastr.error('There was an error!', 'Pricelists')
      }
    );

  }

  initForms() {
    this.basicSearchForm = this.formBuilder.group({
      'location': new FormControl(null, [Validators.required]),
      'from': new FormControl(null, Validators.required),
      'to': new FormControl(null, Validators.required)
    },
      {
        validator: TwoDayValidator('from')
      });

    this.filterForm = this.formBuilder.group({
      'brandType': new FormControl(),
      'modelType': new FormControl(),
      'fuelType': new FormControl(),
      'gearboxType': new FormControl(),
      'bodyType': new FormControl(),
      'priceMin': new FormControl(),
      'priceMax': new FormControl(),
      'mileage': new FormControl(),
      'cdw': new FormControl(),
      'childSeats': new FormControl()
    });
  }

  initFilters() {
    this.searchService.getBrands().subscribe(
      data => {
        this.brands = data;
      },
      error => {
        this.toastr.error('There was an error!', 'Brands')
      }
    );

    this.searchService.getModels().subscribe(
      data => {
        this.models = data;
        this.allModels = data;
      },
      error => {
        this.toastr.error('There was an error!', 'Models')
      }
    );

    this.searchService.getFuels().subscribe(
      data => {
        this.fuels = data;
      },
      error => {
        this.toastr.error('There was an error!', 'Fuels')
      }
    );

    this.searchService.getGearboxes().subscribe(
      data => {
        this.gearboxes = data;
      },
      error => {
        this.toastr.error('There was an error!', 'Gearboxes')
      }
    );

    this.searchService.getBodies().subscribe(
      data => {
        this.bodies = data;
      },
      error => {
        this.toastr.error('There was an error!', 'Bodies')
      }
    );
  }

  priceClick(priceId: number, mileLimit: number, cdw: boolean) {
    let dialogRef = this.dialog.open(PriceDetailsComponent,
      {
        data: {
          pricelistId: priceId,
          mileLimit: mileLimit,
          cdw: cdw
        }
      });
  }

  openDetails(adID: number) {
    let dialogRef = this.dialog.open(AdDetailComponent,
      {
        data: {
          adID: adID,
          pricelist: this.ads.find(obj => { return obj.id === adID }).priceListObj
        }
      });
  }

  searchSubmit() {
    const searchCriteria = new SearchCriteria(
      this.basicSearchForm.value.location,
      new Date(this.basicSearchForm.value.from),
      new Date(this.basicSearchForm.value.to)
    );


    this.adService.search(searchCriteria).subscribe(
      data => {
        this.ads = this.allAds.filter(obj => { return data.find(ad => obj.id === ad.id) })
        this.adsAfterSearch = this.ads;
        this.dataSource = new MatTableDataSource(this.ads);
      },
      error => {
        this.toastr.error('There was an error!', 'Bodies')
      }
    );

  }

  filter() {
    if (this.adsAfterSearch.length > 0) {
      this.ads = this.adsAfterSearch;
    } else {
      this.ads = this.allAds;
    }

    const brand = this.filterForm.value.brandType;
    const model = this.filterForm.value.modelType;
    const fuel = this.filterForm.value.fuelType;
    const gearbox = this.filterForm.value.gearboxType;
    const body = this.filterForm.value.bodyType;
    const priceMin = this.filterForm.value.priceMin;
    const priceMax = this.filterForm.value.priceMax;
    const mileage = this.filterForm.value.mileage;
    const childSeats = this.filterForm.value.childSeats;


    if (brand) {
      this.ads = this.ads.filter(obj => { return obj.vehicleObj.modelType.brandId == brand });
    }

    if (model) {
      this.ads = this.ads.filter(obj => { return obj.vehicleObj.modelType.id == model });
    }

    if (fuel) {
      this.ads = this.ads.filter(obj => { return obj.vehicleObj.fuelType.id == fuel });
    }

    if (gearbox) {
      this.ads = this.ads.filter(obj => { return obj.vehicleObj.gearboxType.id == gearbox });
    }

    if (body) {
      this.ads = this.ads.filter(obj => { return obj.vehicleObj.bodyType.id == body });
    }

    if (priceMin) {
      this.ads = this.ads.filter(obj => { return obj.priceListObj.pricePerDay >= priceMin });
    }

    if (priceMax) {
      this.ads = this.ads.filter(obj => { return obj.priceListObj.pricePerDay <= priceMax });
    }

    if (mileage) {
      this.ads = this.ads.filter(obj => { return obj.vehicleObj.mileage <= mileage });
    }

    if (!this.cdwClean) {
      this.ads = this.ads.filter(obj => { return obj.vehicleObj.cdw == this.cdw });
    }

    if (childSeats) {
      this.ads = this.ads.filter(obj => { return obj.vehicleObj.childSeats == childSeats });
    }
  }

  reset() {
    this.filterForm.reset();
    this.ads = this.allAds;
  }

  sortClick() {
    const sortBy = this.s.name;

    if (sortBy === 'Price') {
      this.sortByPrice();
    }
    else if (sortBy === 'Rate') {
      this.sortByRate();
    }
    else if (sortBy === 'Mileage') {
      this.sortByMileage();
    }

  }

  sortByPrice() {
    if (this.s.asc) {
      this.ads.sort((a, b) => a.priceListObj.pricePerDay - b.priceListObj.pricePerDay);
    }
    else {
      this.ads.sort((a, b) => a.priceListObj.pricePerDay - b.priceListObj.pricePerDay).reverse();
    }
  }

  sortByMileage() {
    if (this.s.asc) {
      this.ads.sort((a, b) => a.vehicleObj.mileage - b.vehicleObj.mileage);
    }
    else {
      this.ads.sort((a, b) => a.vehicleObj.mileage - b.vehicleObj.mileage).reverse();
    }
  }

  sortByRate() {
    if (this.s.asc) {
      this.ads.sort((a, b) => a.averageRate - a.averageRate);
    }
    else {
      this.ads.sort((a, b) => a.averageRate - a.averageRate).reverse();
    }
  }

  brandChanged(brand: VehicleFeature) {
    this.models = [];
    for (let m of this.allModels) {
      if (m.brandId === brand.id) {
        this.models.push(m);
      }
    }
  }

  cdwClick() {
    this.cdwClean = false;
  }

  getMyImage(ad) {
    var img = this.images.find(obj => { return obj.vehicleId === ad.vehicleId })?.image;
    return img;
  }

  public getSantizeUrl(url: any) {
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }

  addToCart(ad: Ad) {

    const from = this.basicSearchForm.value.from;
    const to = this.basicSearchForm.value.to;
    const location = this.basicSearchForm.value.location;

    if (!from || !to || !location) {
      this.toastr.info('Please choose location, from and to date from a form above!', 'Choose dates and location');
      return;
    }

    this.shoppingCartService.addToCart(new ShoppingCartItem(ad.id, from, to));

    this.toastr.success('Successfully added to cart!', 'Success');

    // this.shoppingCartService.getShoppingCartAsObservable().subscribe(
    //   data => {
    //     console.log('Shopping cart', data);
    //   }
    // )


  }
}

