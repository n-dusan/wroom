import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RentsService } from '../../services/rents.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { RentRequest } from 'src/app/modules/shared/models/rent-request.model';
import { Vehicle } from 'src/app/modules/shared/models/vehicle.model';
import { AuthService } from 'src/app/modules/auth/service/auth.service';
import { LoggedUser } from 'src/app/modules/auth/model/logged-user.model';
import { VehicleService } from 'src/app/modules/vehicles/services/vehicle-features/vehicle.service';
import { ToastrService } from 'ngx-toastr';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-bundle-dialog',
  templateUrl: './bundle-dialog.component.html',
  styleUrls: ['./bundle-dialog.component.css']
})
export class BundleDialogComponent implements OnInit {

  vehicleList: Vehicle[] = [];

  loggedUser: LoggedUser;

  requestedUser: LoggedUser;


  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  isLoadingResults: boolean = true;
  displayedColumns: string[] = ['vehicle', 'address', 'date-from', 'date-to', 'requested-user'];

  dataSource: MatTableDataSource<RentRequest> = new MatTableDataSource;

  constructor( public dialogRef: MatDialogRef<any>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private rentService: RentsService,
    private authService: AuthService,
    private vehicleService: VehicleService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    console.log('my data', this.data)
    if(this.data) {
      this.dataSource.data = []
      this.refresh();
      this.dataSource.paginator = this.paginator;
    }
  }

  bless() {
    this.rentService.acceptBundle(this.data.bundleId).subscribe((data: RentRequest[]) => {
      this.toastr.success('Snippy snappy', 'Bang, blessed')
      this.dialogRef.close();
    }, error => this.toastr.error('Went wrong :(', 'Bad'))
  }

  decline() {
    this.rentService.declineBundle(this.data.bundleId).subscribe((data: RentRequest[]) => {
      this.toastr.success('Snippy snappy', 'Bang, declined')
      this.dialogRef.close();
    }, error => this.toastr.error('Went wrong :(', 'Bad'))
  }


  vehicleName(request: RentRequest): String {
      const vehicle = this.vehicleList.find(x => x.id == request.ad.vehicleId);
      const modelType = vehicle?.modelType?.name;
      const brandType = vehicle?.brandType?.name;
      const ret = brandType + " " + modelType;
      return ret;
  }


  refresh() {
    this.authService.whoami().subscribe(data => {

      this.loggedUser = data;

      this.vehicleService.getAllActiveForUser(this.loggedUser.id).subscribe((data: Vehicle[]) => {
        this.vehicleList = data;
      })

      this.rentService.getBundledRequests(this.data.bundleId).subscribe((data: RentRequest[]) => {
        this.dataSource.data = data;
        this.isLoadingResults = false;
        console.log('my data source', this.dataSource.data)

        this.authService.getUser(data[0].requestedUserId).subscribe((user: LoggedUser) => {
          this.requestedUser = user;
          console.log('my requested user', this.requestedUser)
        })
      })


    },
      error => {
        console.log(error)
      }

    );

  }


}
