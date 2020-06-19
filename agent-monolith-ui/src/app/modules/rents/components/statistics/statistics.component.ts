import { Component, OnInit } from '@angular/core';


import { Vehicle } from 'src/app/modules/shared/models/vehicle.model';
import { VehicleChart } from 'src/app/modules/shared/models/vehicle-chart.model';
import { VehicleService } from 'src/app/modules/vehicles/services/vehicle-features/vehicle.service';
import { AuthService } from 'src/app/modules/auth/service/auth.service';
import { LoggedUser } from 'src/app/modules/auth/model/logged-user.model';

import { CommentsService } from 'src/app/modules/shared/service/comments.service';
import { RentReportService } from '../../services/rent-report.service';
import { Comment } from 'src/app/modules/shared/models/comment.model';
import { RentReport } from 'src/app/modules/shared/models/rent-report.model';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  multi: any[];
  view: any[] = [700, 400];

  loading: boolean = false;

  // options
  showXAxis: boolean = true;
  showYAxis: boolean = true;
  gradient: boolean = false;
  showLegend: boolean = true;
  legendPosition: string = 'below';
  showXAxisLabel: boolean = true;
  yAxisLabel: string = 'Your vehicles';
  showYAxisLabel: boolean = true;
  xAxisLabel = 'Number of miles/comments/rates';

  colorScheme = {
    domain: ['#5AA454', '#C7B42C', '#AAAAAA']
  };
  schemeType: string = 'ordinal';

  vehiceList: Vehicle[] = [];
  multiList: VehicleChart[] = [];

  constructor(private vehicleService: VehicleService,
    private authService: AuthService,
    private commentsService: CommentsService,
    private rentReportService: RentReportService) {}

  ngOnInit(): void {

    this.authService.whoami().subscribe((user: LoggedUser) => {
      this.loading = true;

      this.vehicleService.getAllActiveForUser(user.id).subscribe((data: Vehicle[]) => {
        this.vehiceList = data;
        //for each vehicle iterate and query, append to multiList
        for(let i = 0; i < this.vehiceList.length; i++) {
          let vehicle = this.vehiceList[i];
          let vehicleId = this.vehiceList[i].id;

          console.log('vehicle', vehicle.brandType)

          let chart = {
            'name': vehicle.brandType.name + ' ' + vehicle.modelType.name,
            'series': []
          }

          this.commentsService.getForVehicle(vehicleId).subscribe((comments: Comment[]) => {
            console.log('am i here', comments)
            this.commentsService.getAvgVehicle(vehicleId).subscribe((avgRate: number) => {
              console.log('avg rate', avgRate)
              this.rentReportService.getForVehicle(vehicleId).subscribe((reports: RentReport[]) => {
                console.log('le charts', reports)
                let traveledMiles = 0;
                for(let i = 0; i < reports.length; i++) {
                    traveledMiles += reports[i].traveledMiles
                }
               // traveledMiles += vehicle.mileage;

                chart.series.push({
                  'name': 'traveled miles',
                  'value': traveledMiles
                })
                chart.series.push(
                  {
                    'name': 'comments',
                    'value': comments.length ? comments.length : 0
                  }
                );
                chart.series.push({
                  'name': 'average rate',
                  'value': avgRate ? avgRate : 0
                });
                console.log('chart', chart)
                this.multiList.push(chart);

                if(i == this.vehiceList.length - 1) {
                  this.loading = false;
                  console.log('multilist', this.multiList)
                  this.multi = this.multiList
                }
              })
            })
          })


        }
        console.log('list ', this.vehiceList)
      })
    });


  }

  onSelect(data): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }


}
