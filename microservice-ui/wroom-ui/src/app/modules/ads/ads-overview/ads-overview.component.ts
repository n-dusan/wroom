import { Component, OnInit, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Ad } from '../model/ad.model';
import { MatDialog } from '@angular/material/dialog';
import { AdsService } from '../services/ads.service';
import { takeUntil } from 'rxjs/operators';
import { AuthService } from '../../auth/service/auth.service';
import { LoggedUser } from '../../auth/model/logged-user.model';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AdDetailComponent } from '../../search/components/ad-detail/ad-detail.component';
import { PriceListService } from '../services/price-list.service';
import { PriceList } from '../../shared/models/price-list.model';
import { VehicleOccupancyComponent } from '../../vehicles/vehicle-occupancy/vehicle-occupancy.component';

@Component({
  selector: 'app-ads-overview',
  templateUrl: './ads-overview.component.html',
  styleUrls: ['./ads-overview.component.css']
})
export class AdsOverviewComponent implements OnInit {

  private ngUnsubscribe = new Subject();
  loggedUser: LoggedUser;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  isLoadingResults: boolean = true;
  displayedColumns: string[] = ['from', 'to', 'mile-limit', 'address', 'details', 'gps', 'edit', 'delete'];

  dataSource: MatTableDataSource<Ad> = new MatTableDataSource;

  constructor(
    private dialog: MatDialog,
    private adsService: AdsService,
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private priceListService: PriceListService) { }

  ngOnInit(): void {
    this.authService.whoami().subscribe(data => {
      this.loggedUser = data;
      this.dataSource.data = [];
      this.refresh();
      this.dataSource.paginator = this.paginator;
    },
      error => {
        console.log(error)
      }

    );
  }

  refresh() {
    this.adsService.getAllActiveForUser(this.loggedUser.id).pipe(takeUntil(this.ngUnsubscribe)).subscribe((data: Ad[]) => {
      this.dataSource.data = data;
      console.log('my data', data)
    })
  }

  onAddAdClick() {
    this.router.navigate(['../new'], { relativeTo: this.activatedRoute });
  }

  onEditAdClick(ad: Ad) {
    this.router.navigate(['../new/' + ad.id], { relativeTo: this.activatedRoute });
  }

  onDeleteAdClick(ad: Ad) {
    console.log('delete ad', ad)
    this.adsService.deleteAd(ad.id).subscribe(() => {
      this.refresh()
    })
  }


  viewDetails(ad: Ad) {
    let dialogPriceList: PriceList;

    this.priceListService.findById(ad.priceListId).subscribe((priceList: PriceList) => {
      dialogPriceList = priceList;
    })

    // dialogRef.afterClosed().pipe(take(1)).subscribe((vehicle: Vehicle ) => {
    //   if(vehicle) {
    //     console.log('my vehicle', vehicle)
    //     this.vehicle = vehicle;
    //     this.adForm.get('vehicle').setValue('Selected ' + this.vehicle.brandType.name + ' ' + this.vehicle.modelType.name);
    //   }
    // });
  }


  onOccupancyClick() {
    const dialogRef = this.dialog.open(VehicleOccupancyComponent, {
      width: '400px',
      height: '400px'
    });
    dialogRef.afterClosed().subscribe(() => {
    });
  }


}
