import { Component, OnInit, Inject } from '@angular/core';
import { AuthService } from '../../../auth/service/auth.service';
import { RentsService } from '../../services/rents.service';
import { LoggedUser } from '../../../auth/model/logged-user.model';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { RentRequest } from '../../../shared/models/rent-request.model';
import { VehicleService } from '../../../vehicles/services/vehicle-features/vehicle.service';
import { Vehicle } from '../../../shared/models/vehicle.model';
import { ToastrService } from 'ngx-toastr';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BundleDialogComponent } from '../bundle-dialog/bundle-dialog.component';
import { PriceList } from 'src/app/modules/search/model/price-list.model';
import { PriceListService } from 'src/app/modules/ads/services/price-list.service';
import { RentReportDialogComponent } from '../rent-report-dialog/rent-report-dialog.component';
import { Ad } from 'src/app/modules/shared/models/ad.model';
import { NewCommentComponent } from 'src/app/modules/ads/comments/new-comment/new-comment.component';

import { RentReport } from 'src/app/modules/shared/models/rent-report.model';
import { RentReportService } from '../../services/rent-report.service';

@Component({
  selector: 'app-vehicle-occupancy-list',
  templateUrl: './vehicle-occupancy-list.component.html',
  styleUrls: ['./vehicle-occupancy-list.component.css']
})
export class VehicleOccupancyListComponent implements OnInit {

  loggedUser: LoggedUser;
  private ngUnsubscribe = new Subject();

  loadedVehicles: boolean = false;
  loadedPriceLists: boolean = false;


  requestList: RentRequest[] = [];
  vehicleList: Vehicle[] = [];
  priceListList: PriceList[] = [];
  public dateFrom: any;
  public dateTo: any;
  vehicle: Vehicle = new Vehicle();
  paidList: RentRequest[] = [];
  pendingList: RentRequest[] = [];
  canceledList: RentRequest[] = [];
  reservedList: RentRequest[] = [];
  physicallyReservedList: RentRequest[] = [];
  completedList: RentRequest[] = [];


  reportList: RentReport[] = [];

  loadingPending = true;

  constructor(private authService: AuthService,
    private rentService: RentsService,
    private vehicleService: VehicleService,
    private toastr: ToastrService,
    private dialog: MatDialog,
    private priceListService: PriceListService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private rentReportService: RentReportService) { }

  ngOnInit(): void {
    this.refresh();
  }

  vehicleName(request: RentRequest): String {
      const vehicle = this.vehicleList.find(x => x.id == request.ad.vehicleId);
      const modelType = vehicle?.modelType?.name;
      const brandType = vehicle?.brandType?.name;
      const ret = brandType + " " + modelType;
      return ret;
  }


  declineRequest(request: RentRequest) {
    this.rentService.decline(request.id).subscribe((responseRequest: RentRequest) => {
      this.toastr.success('Declined', 'Declined that bad boy');
      console.log(responseRequest);
      this.ngOnInit();
    }, error => {
      this.toastr.error('Welp', error.error);
    })
  }

  acceptRequest(request: RentRequest) {
    this.rentService.accept(request.id).subscribe((responseRequest: RentRequest) => {
      this.toastr.success('Successfully reserved', 'Accepted');
      this.ngOnInit();
    }, error => {
      this.toastr.error(error.error, 'Went wrong');
    })
  }

  viewBundle(request: RentRequest) {
    console.log('my bundle', request);
    let dialogRef = this.dialog.open(BundleDialogComponent, {
      data: {
        bundleId: request.bundleId
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.refresh()
    });
  }

  pricePerDay(request: RentRequest){
    const priceList = this.priceListList.find(x => x.id == request.ad.priceListId);

    return priceList?.pricePerDay;
  }

  pricePerMile(request: RentRequest) {
    const priceList = this.priceListList.find(x => x.id == request.ad.priceListId);

    return priceList?.pricePerMile
  }

  priceCDW(request: RentRequest) {
    const priceList = this.priceListList.find(x => x.id == request.ad.priceListId);
    const vehicle = this.vehicleList.find(x => x.id == request.ad.vehicleId);

    if(vehicle?.cdw) {
      return priceList?.priceCDW
    }

    return 0
  }

  discount(request: RentRequest) {
    const priceList = this.priceListList.find(x => x.id == request.ad.priceListId);

    return priceList?.discount
  }


  calculatePrice(request: RentRequest): string {
    const vehicle = this.vehicleList.find(x => x.id == request.ad.vehicleId);
    const priceList = this.priceListList.find(x => x.id == request.ad.priceListId);


    let toDate = new Date(request.toDate);
    let fromDate = new Date(request.fromDate);

    var Difference_In_Time = toDate.getTime() - fromDate.getTime();

    // To calculate the no. of days between two dates
    var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);

    if(vehicle?.cdw) {
      let total = +Difference_In_Days * +priceList?.pricePerDay + priceList?.priceCDW
      return '<b>' + Difference_In_Days + '</b> d x ' + '<b>' + priceList?.pricePerDay + '</b>$ + <b>' + priceList?.priceCDW + '$</b> cdw = '
      + '<b>' +  total + '$</b>';
    }

    let total = +Difference_In_Days * +priceList?.pricePerDay;
    return '<b>' + Difference_In_Days + '</b> d x ' + '<b>' + priceList?.pricePerDay + '$</b> = '
      + '<b>' +  total + '$</b>';
  }

  refresh() {
    this.canceledList = [];
    this.completedList = [];
    this.pendingList = [];
    this.physicallyReservedList = [];
    this.reservedList = [];

    this.authService.whoami().subscribe(data => {

      this.loggedUser = data;



      this.vehicleService.getAll().subscribe((data: Vehicle[]) => {
        this.vehicleList = data;
        this.loadedVehicles = true;
      })

      this.priceListService.findAll().subscribe((data: PriceList[]) => {
        this.priceListList = data;
        this.loadedPriceLists = true;
      })

      this.rentReportService.findAll().subscribe((data: RentReport[]) => {
        this.reportList = data;
      })

      this.rentService.getAllActiveForUser(this.loggedUser.id).pipe(takeUntil(this.ngUnsubscribe)).subscribe((data: RentRequest[]) => {
        this.requestList = data;

        //dont show everything from pending, show only what's available
        this.rentService.getPendingForUser(this.loggedUser.id).subscribe((data: RentRequest[]) => {
          this.pendingList = data;
          this.loadingPending = false;
        })

        for (let r of this.requestList) {

          this.dateFrom = new Date(r.fromDate);
          this.dateTo = new Date(r.toDate);
          if (r.status == 'PAID') {
            this.paidList.push(r);
          }

          else if (r.status == 'CANCELED') {
            this.canceledList.push(r);
          } else if (r.status == 'RESERVED') {
            this.reservedList.push(r);
          } else if (r.status == 'PHYSICALLY_RESERVED'){
            this.physicallyReservedList.push(r);
          } else if (r.status == 'COMPLETED') {
            this.completedList.push(r);
          }
        }
      })

    },
      error => {
        console.log(error)
      }

    );

  }


  createReport(request: RentRequest) {

    let dialogRef = this.dialog.open(RentReportDialogComponent, {
      data: {
        request: request
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.refresh()
    });
  }

  addComment(ad: Ad){
    const dialogRef = this.dialog.open(NewCommentComponent, {
      width: '500px',
      height: '400px',
      data: ad
    });
    dialogRef.afterClosed().subscribe(result => {

    });
  }

  checkRequest(request: RentRequest) {
    let report = this.reportList.find(x => x.rentRequestId == request.id);

    if(report) {
      return true;
    }

    return false;
  }

  showReport(request: RentRequest) {
    let report = this.reportList.find(x => x.rentRequestId == request.id);
    return 'Miles passed:' + '<b> ' + report.traveledMiles + '</b> <br/> Note: ' + '<b>' + (report.note ? report.note : 'unspecified') + '</b>'
  }

}
