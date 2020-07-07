import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import { VehicleService } from '../services/vehicle-features/vehicle.service';
import { Vehicle } from '../../shared/models/vehicle.model';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { VehicleDetailsComponent } from '../vehicle-details/vehicle-details.component';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../auth/service/auth.service';
import { Subject } from 'rxjs';
import { LoggedUser } from '../../auth/model/logged-user.model';
import { MatPaginator } from '@angular/material/paginator';
import { takeUntil } from 'rxjs/operators';
import { EditVehicleComponent } from '../edit-vehicle/edit-vehicle.component';
import { NewVehicleComponent } from '../new-vehicle/new-vehicle.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.css']
})
export class VehicleListComponent implements OnInit {
  private ngUnsubscribe = new Subject();
  loggedUser: LoggedUser;
  isEdit: boolean = true;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  displayedColumns: string[] = ['brandType', 'modelType', 'details', 'edit', 'delete'];
  dataVehicleSource: MatTableDataSource<Vehicle> = new MatTableDataSource;
  constructor(private vehicleService: VehicleService,
              public dialog: MatDialog,
              private authService: AuthService,
              private toastr: ToastrService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private router: Router,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    //this.loadVehicleData();
    this.authService.whoami().subscribe(data => {
      this.loggedUser = data;
      this.dataVehicleSource.data = [];
      this.refresh();
      this.dataVehicleSource.paginator = this.paginator;
    },
    error => {
      console.log(error)
    }

  );
  }

  refresh() {
    this.vehicleService.getAllActiveForUser(this.loggedUser.id).pipe(takeUntil(this.ngUnsubscribe)).subscribe((data: Vehicle[]) => {
      this.dataVehicleSource.data = data;
      console.log('my data', data)
    })
  }

  loadVehicleData(){
    this.vehicleService.getVehicles().subscribe(
      data => {
        this.dataVehicleSource = new MatTableDataSource(data);

      }
    );
  }

  vehicleDetails(element: any): void {
    console.log(element.childSeats);
    const dialogRef = this.dialog.open(VehicleDetailsComponent, {
      width: '500px',
      height: '700px',
      data: element
    });
    dialogRef.afterClosed().subscribe(result => {

    });
  }

  onAddVehicleClick() {
    this.router.navigate(['../new'], { relativeTo: this.activatedRoute });
  }

  onEditVehicleClick(element: Vehicle) {
    console.log('element', element)
    const dialogRef = this.dialog.open(EditVehicleComponent, {
      width: '300px',
      height: '700px',
      data: {isEdit: this.isEdit=true, element}
    });
    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
  }

  onDeleteVehicleClick(element: Vehicle) {
    this.vehicleService.delete(element.id).subscribe(
      response => {
        this.refresh();
        this.toastr.success('You have successfully deleted Vehicle!', 'Success')
    }, error => {
      
      this.toastr.error('Insufficient rights, please contact admin', 'Error')
    })
  }

}
