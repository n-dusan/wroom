import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../auth/service/auth.service';
import { RentsService } from '../../services/rents.service';
import { LoggedUser } from '../../../auth/model/logged-user.model';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { RentRequest } from '../../../shared/models/rent-request.model';
import { VehicleService } from '../../../vehicles/services/vehicle-features/vehicle.service';
import { Vehicle } from '../../../shared/models/vehicle.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-vehicle-occupancy-list',
  templateUrl: './vehicle-occupancy-list.component.html',
  styleUrls: ['./vehicle-occupancy-list.component.css']
})
export class VehicleOccupancyListComponent implements OnInit {

  loggedUser: LoggedUser;
  private ngUnsubscribe = new Subject();
  requestList: RentRequest[] = [];
  vehicleList: Vehicle[] = [];
  public dateFrom: any;
  public dateTo: any;
  vehicle: Vehicle = new Vehicle();
  paidList: RentRequest[] = [];
  pendingList: RentRequest[] = [];
  canceledList: RentRequest[] = [];
  reservedList: RentRequest[] = [];
  physicallyReservedList: RentRequest[] = [];

  loadingPending = true;

  constructor(private authService: AuthService,
    private rentService: RentsService,
    private vehicleService: VehicleService,
    private toastr: ToastrService) { }

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
    console.log('declinig', request);
    this.rentService.decline(request.id).subscribe((responseRequest: RentRequest) => {
      this.toastr.success('Declined', 'Declined that bad boy');
      console.log(responseRequest);
      this.ngOnInit();
    }, error => {
      this.toastr.error('Welp', error.error);
    })
  }

  acceptRequest(request: RentRequest) {
    console.log('acc', request);
    this.rentService.accept(request.id).subscribe((responseRequest: RentRequest) => {
      this.toastr.success('Successfully reserved', 'Accepted');
      this.ngOnInit();
    }, error => {
      this.toastr.error(error.error, 'Went wrong');
    })
  }

  viewBundle(request: RentRequest) {
    console.log('my bundle', request);
  }

  refresh() {
    this.authService.whoami().subscribe(data => {

      this.loggedUser = data;

      this.vehicleService.getAllActiveForUser(this.loggedUser.id).subscribe((data: Vehicle[]) => {
        this.vehicleList = data;
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
          } else {
            this.physicallyReservedList.push(r);
          }
        }
      })

    },
      error => {
        console.log(error)
      }

    );

  }

}
