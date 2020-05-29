import { Component, OnInit, ViewChild } from '@angular/core';
import { Ad } from '../../model/ad.model';
import { AdService } from '../../service/ad.service';
import { ToastrService } from 'ngx-toastr';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { AdDetailComponent } from '../ad-detail/ad-detail.component';
import { Vehicle } from '../../model/vehicle.model';
import { VehicleService } from '../../service/vehicle.service';
import { PriceList } from '../../model/price-list.model';
import { AdLocation } from '../../model/ad-location.model';
import { PriceListService } from '../../service/price-list.service';
import { VehicleFeature } from '../../model/vehicle-feature.model';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { SearchCriteria } from '../../model/search-criteria.model';
import { MatTableDataSource } from '@angular/material/table';
import { TwoDayValidator } from '../../validators/two-days.validator';

@Component({
  selector: 'app-search-ads',
  templateUrl: './search-ads.component.html',
  styleUrls: ['./search-ads.component.css']
})
export class SearchAdsComponent implements OnInit {

  ads: Ad[] = [];
  adsAfterSearch: Ad[] = [];
  allAds: Ad[] = [];
  vehicles: Vehicle[] = [];
  locations: AdLocation[] = [];
  priceLists: PriceList[] = [];

  // Filters
  brands: VehicleFeature[] = [];
  models: VehicleFeature[] = [];
  allModels: VehicleFeature[] = [];
  fuels: VehicleFeature[] = [];
  gearboxes: VehicleFeature[] = [];
  bodies: VehicleFeature[] = [];

  dataSource: MatTableDataSource<Ad>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  basicSearchForm: FormGroup;
  filterForm: FormGroup;
  cdw: boolean = false;
  cdwClean = true;

  constructor(private adService: AdService,
    private vehicleService: VehicleService,
    private pricelistService: PriceListService,
    private toastr: ToastrService,
    private dialog: MatDialog,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.initAds();
    this.initFilters();
    this.initForms();

  }

  initAds() {
    // Fetching all necessary data
    this.adService.all().subscribe(
      data => {
        this.ads = data;
        this.allAds = data;
        this.adsAfterSearch = data;
        this.dataSource = new MatTableDataSource(this.allAds);
      },
      error => {
        this.toastr.error('There was an error!', 'Ads')
      }
    );

    this.vehicleService.all().subscribe(
      data => {
        this.vehicles = data;
        console.log('vehicles', this.vehicles)

        for (let v of this.vehicles) {
          var ad = this.ads.find(obj => { return obj.vehicleId === v.id });
          if (ad) {
            ad.vehicleObj = v;
          }
        }
      },
      error => {
        this.toastr.error('There was an error!', 'Vehicles')
      }
    );

    this.adService.getLocations().subscribe(
      data => {
        this.locations = data;
        // console.log('locations', this.locations)

        for (let a of this.ads) {
          a.locationObj = this.locations.find(obj => { return obj.id === a.locationId });
        }
      },
      error => {
        this.toastr.error('There was an error!', 'Locations')
      }
    );

    this.pricelistService.all().subscribe(
      data => {
        this.priceLists = data;
        // console.log('pricelists',this.priceLists)

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
    this.vehicleService.getBrands().subscribe(
      data => {
        this.brands = data;
        // console.log('brands', this.brands)
      },
      error => {
        this.toastr.error('There was an error!', 'Brands')
      }
    );

    this.vehicleService.getModels().subscribe(
      data => {
        this.models = data;
        this.allModels = data;
        // console.log('models', this.models)
      },
      error => {
        this.toastr.error('There was an error!', 'Models')
      }
    );

    this.vehicleService.getFuels().subscribe(
      data => {
        this.fuels = data;
        // console.log('fuels', this.fuels)
      },
      error => {
        this.toastr.error('There was an error!', 'Fuels')
      }
    );

    this.vehicleService.getGearboxes().subscribe(
      data => {
        this.gearboxes = data;
        // console.log('gearboxes', this.gearboxes)
      },
      error => {
        this.toastr.error('There was an error!', 'Gearboxes')
      }
    );

    this.vehicleService.getBodies().subscribe(
      data => {
        this.bodies = data;
        // console.log('bodies', this.bodies)
      },
      error => {
        this.toastr.error('There was an error!', 'Bodies')
      }
    );
  }

  openDetails(adID: number) {
    let dialogRef = this.dialog.open(AdDetailComponent,
      {
        data: { adID: adID }
      });
    // dialogRef.afterClosed().pipe(take(1)).subscribe((vehicle: Vehicle ) => {
    //   if(vehicle) {
    //     console.log('my vehicle', vehicle)
    //     this.vehicle = vehicle;
    //     this.adForm.get('vehicle').setValue('Selected ' + this.vehicle.brandType.name + ' ' + this.vehicle.modelType.name);
    //   }
    // });
  }

  searchSubmit() {
    console.log(this.basicSearchForm);

    const searchCriteria = new SearchCriteria(
      this.basicSearchForm.value.location,
      new Date(this.basicSearchForm.value.from),
      new Date(this.basicSearchForm.value.to)
    );

    this.adService.search(searchCriteria).subscribe(
      data => {
        console.log(data);
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
    if(this.adsAfterSearch.length > 0) {
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

    if(!this.cdwClean){
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

}
