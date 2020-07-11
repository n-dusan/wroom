import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/modules/shared/service/auth.service';
import { LoggedUser } from 'src/app/modules/auth/model/logged-user.model';
import { RentsService } from '../../services/rents.service';
import { RentRequest } from 'src/app/modules/shared/models/rent-request.model';
import { MatDialog } from '@angular/material/dialog';
import { ContactOwnerComponent } from '../contact-owner/contact-owner.component';
import { PriceList } from 'src/app/modules/search/model/price-list.model';
import { Vehicle } from 'src/app/modules/shared/models/vehicle.model';
import { VehicleService } from 'src/app/modules/vehicles/services/vehicle-features/vehicle.service';
import { PriceListService } from 'src/app/modules/ads/services/price-list.service';
import { ToastrService } from 'ngx-toastr';
import { RentReportService } from '../../services/rent-report.service';
import { RentReport } from 'src/app/modules/shared/models/rent-report.model';
import { Ad } from 'src/app/modules/shared/models/ad.model';
import { NewCommentComponent } from 'src/app/modules/ads/comments/new-comment/new-comment.component';

@Component({
  selector: 'app-requests-overview',
  templateUrl: './requests-overview.component.html',
  styleUrls: ['./requests-overview.component.css']
})
export class RequestsOverviewComponent implements OnInit {

  loggedUser: LoggedUser;
  loaded: boolean = false;
  loadedVehicles: boolean = false;
  loadedPriceLists: boolean = false;

  priceListList: PriceList[] = [];
  vehicleList: Vehicle[] = [];

  pending: RentRequest[] = [];
  reserved: RentRequest[] = [];
  paid: RentRequest[] = [];
  canceled: RentRequest[] = [];
  completed: RentRequest[] = [];


  reportList: RentReport[] = [];

  constructor(private authService: AuthService,
    private rentsService: RentsService,
    private dialog: MatDialog,
    private priceListService: PriceListService,
    private vehicleService: VehicleService,
    private toastr: ToastrService,
    private rentReportService: RentReportService) { }

  ngOnInit(): void {
    this.refresh();
  }

  sendMessage(request: RentRequest) {

    console.log(request)
    let dialogRef = this.dialog.open(ContactOwnerComponent,
      {
        data: {
          request: request
        }
      });

  }



  pricePerDay(request: RentRequest) {
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

    if (vehicle?.cdw) {
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

    let total =  +Difference_In_Days * +priceList?.pricePerDay;

    let response = '<b>' + Difference_In_Days + '</b> d x ' + '<b>' + priceList?.pricePerDay + '$</b> = '
    + '<b>' +  total + '$</b>';

    if(Difference_In_Days > 30) {
      let total =  +Difference_In_Days * +priceList?.pricePerDay * +priceList?.discount/100;
      response += '<br> <b>total after discount </b> <b>' +  total + '$</b>';
    }
    return response;
  }

  vehicleName(request: RentRequest): String {
    const vehicle = this.vehicleList.find(x => x.id == request?.ad.vehicleId);
    const modelType = vehicle?.modelType?.name;
    const brandType = vehicle?.brandType?.name;
    const ret = brandType + " " + modelType;
    return ret;
  }

  payRequest(request: RentRequest) {
    if (request.bundleId) {
      this.rentsService.payBundle(request.bundleId).subscribe((response: RentRequest[]) => {
        this.toastr.success('You did well', 'Congrats');
        console.log('paid bundle', response)
        for (let r of response) {
          const paid = this.reserved.find(obj => { return obj.id === r?.id });
          const i = this.reserved.indexOf(paid);
          this.reserved.splice(i, 1);
        }
        this.refresh();
      }, error => this.toastr.error('Went wrong with payment', 'Payment'))

    } else {
      this.rentsService.payRequest(request.id).subscribe((response: RentRequest[]) => {
        this.toastr.success('You did well', 'Congrats');
        const paid = this.reserved.find(obj => { return obj.id === response[0]?.id });
        const i = this.reserved.indexOf(paid);
        this.reserved.splice(i, 1);
        this.refresh();
      }, error => this.toastr.error('Went wrong with payment', 'Payment'))
    }
  }

  refresh() {
    this.authService.whoami().subscribe(data => {
      this.loggedUser = data;

      this.rentReportService.findAll().subscribe((data: RentReport[]) => {
        this.reportList = data;
      })

      this.rentsService.getRequestedForUser(this.loggedUser.id).subscribe(
        data => {
          console.log(data)

          for (let request of data) {
            if (request.status === 'PENDING') {
              this.pending.push(request);
            }
            else if (request.status === 'RESERVED') {
              this.reserved.push(request);
            }
            else if (request.status === 'PAID') {
              this.paid.push(request);
            }
            else if (request.status === 'CANCELED') {
              this.canceled.push(request);
            }
            else if (request.status === 'COMPLETED') {
              this.completed.push(request);
            }
          }

          this.loaded = true;
        },
        error => {
          this.loaded = true;

        }
      )

      this.vehicleService.getAll().subscribe((data: Vehicle[]) => {
        this.vehicleList = data;
        this.loadedVehicles = true;
      })

      this.priceListService.findAll().subscribe((data: PriceList[]) => {
        this.priceListList = data;
        this.loadedPriceLists = true;
      })
    });
  }


  cancelRequest(request: RentRequest) {
    if (request.bundleId) {
      this.rentsService.declineBundle(request.bundleId).subscribe((response: RentRequest[]) => {
        this.toastr.success('Woooohooo', 'Canceled le bundle')
        this.ngOnInit();
      }, error => this.toastr.error(':(', 'Went south'))
    } else {
      this.rentsService.decline(request.id).subscribe((response: RentRequest) => {
        this.toastr.success('Woooohooo', 'Canceled the request')
        this.ngOnInit();
      }, error => this.toastr.error(':(', 'Went south'))
    }
  }


  checkRequest(request: RentRequest) {
    let report = this.reportList.find(x => x.rentRequestId == request.id);

    if (report) {
      return true;
    }

    return false;
  }

  showReport(request: RentRequest) {
    let report = this.reportList.find(x => x.rentRequestId == request.id);
    return 'Miles passed:' + '<b> ' + report.traveledMiles + '</b> <br/> Note: ' + '<b>' + (report.note ? report.note : 'unspecified') + '</b>'
  }

  addComment(ad: Ad) {
    const dialogRef = this.dialog.open(NewCommentComponent, {
      width: '500px',
      height: '400px',
      data: ad
    });
    dialogRef.afterClosed().subscribe(result => {

    });
  }

}
