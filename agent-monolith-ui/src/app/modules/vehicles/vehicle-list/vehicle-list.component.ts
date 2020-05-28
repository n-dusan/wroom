import { Component, OnInit, Inject } from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import { VehicleService } from '../services/vehicle-features/vehicle.service';
import { Vehicle } from '../../shared/models/vehicle.model';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { VehicleDetailsComponent } from '../vehicle-details/vehicle-details.component';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.css']
})
export class VehicleListComponent implements OnInit {
  displayedColumns: string[] = ['brandType', 'modelType', 'details', 'edit', 'delete'];
  dataVehicleSource : MatTableDataSource<Vehicle>;
  constructor(private vehicleService: VehicleService,
              public dialog: MatDialog,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private router: Router,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadVehicleData();
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
      height: '400px',
      data: element
    });
    dialogRef.afterClosed().subscribe(result => {

    });
  }

  onAddVehicleClick() {
    this.router.navigate(['../new'], { relativeTo: this.activatedRoute });
  }

  onEditVehicleClick(element: any) {
    //todo
  }

  onDeleteVehicleClick(element: any) {
    //todo
  }

}