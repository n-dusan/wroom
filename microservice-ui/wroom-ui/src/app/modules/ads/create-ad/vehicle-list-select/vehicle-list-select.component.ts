import { Component, OnInit, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Vehicle } from 'src/app/modules/shared/models/vehicle.model';
import { AdsService } from '../../services/ads.service';
import { MatDialogRef } from '@angular/material/dialog';
import { takeUntil } from 'rxjs/operators';
import { AuthService } from 'src/app/modules/auth/service/auth.service';

@Component({
  selector: 'app-vehicle-list-select',
  templateUrl: './vehicle-list-select.component.html',
  styleUrls: ['./vehicle-list-select.component.css']
})
export class VehicleListSelectComponent implements OnInit {

  private ngUnsubscribe = new Subject();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  isLoadingResults: boolean = true;
  displayedColumns: string[] = ['select', 'brand', 'model', 'gearbox', 'fuel', 'body', 'mileage'];

  dataSource: MatTableDataSource<Vehicle> = new MatTableDataSource;

  selectedVehicle: string = '';

  constructor(
    private adsService: AdsService,
    public dialogRef: MatDialogRef<any>
  ) { }


  ngOnInit(): void {

    this.dataSource.data = [];
    this.fetch();
    this.dataSource.paginator = this.paginator;
  }

  fetch() {
    this.adsService.getAllVehicles().pipe(takeUntil(this.ngUnsubscribe)).subscribe((result: Vehicle[]) => {
      this.dataSource.data = result;
    })
  }

  onSelectChange(row: any) {
    //todo: on row selected, forward the data to the create-ad component (via dialog close data)
    console.log('wat i got', row)
    this.dialogRef.close(row);
  }
}
