import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../auth/service/auth.service';
import { RentsService } from '../../services/rents.service';
import { LoggedUser } from '../../../auth/model/logged-user.model';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { RentRequest } from '../../../shared/models/rent-request';
import { VehicleService } from '../../../vehicles/services/vehicle-features/vehicle.service';
import { Vehicle } from '../../../shared/models/vehicle.model';

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
  vehicleId: number;
  public dateFrom: any;
  public dateTo: any;
  vehicle: Vehicle = new Vehicle();
  paidList: RentRequest[] = [];
  pendingList: RentRequest[] = [];
  canceledList: RentRequest[] = [];
  reservedList: RentRequest[] = [];
  physicallyReservedList: RentRequest[] = [];

  constructor(private authService: AuthService,
    private rentService: RentsService,
    private vehicleService: VehicleService) { }

  ngOnInit(): void {
    this.authService.whoami().subscribe(data => {

      this.loggedUser = data;

      this.vehicleService.getAllActiveForUser(this.loggedUser.id).subscribe((data: Vehicle[]) => {
        this.vehicleList = data;
      })

      this.rentService.getAllActiveForUser(this.loggedUser.id).pipe(takeUntil(this.ngUnsubscribe)).subscribe((data: RentRequest[]) => {
        this.requestList = data;
        for (let r of this.requestList) {
          
          this.dateFrom = new Date(r.fromDate);
          this.dateTo = new Date(r.toDate);
          if (r.status == 'PAID') {
            this.paidList.push(r);
          } else if (r.status == 'PENDING') {
            this.pendingList.push(r);
          } else if (r.status == 'CANCELED') {
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

  vehicleName(request: RentRequest): String{
      const vehicle = this.vehicleList.find(x => x.id == request.ad.vehicleId); 
      const modelType = vehicle?.modelType?.name;  
      const brandType = vehicle?.brandType?.name;   
      const ret = brandType + " " + modelType; 
  return ret;
  }

}
