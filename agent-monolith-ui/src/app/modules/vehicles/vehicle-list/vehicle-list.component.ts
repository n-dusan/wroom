import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import { VehicleService } from '../services/vehicle-features/vehicle.service';
import { Vehicle } from '../../shared/models/vehicle.model';

@Component({
  selector: 'app-vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.css']
})
export class VehicleListComponent implements OnInit {
  displayedColumns: string[] = ['brandType', 'modelType'];
  dataVehicleSource : MatTableDataSource<Vehicle>;
  constructor(private vehicleService: VehicleService) { }

  ngOnInit(): void {
    this.loadVehicleData();
  }

  loadVehicleData(){
    this.vehicleService.getVehicles().subscribe(
      data => {
        console.log(data);
        
        this.dataVehicleSource = new MatTableDataSource(data);
        
      }
    );  
  }

}
