import { Component, OnInit } from '@angular/core';

import { Vehicle } from 'src/app/modules/shared/models/vehicle.model';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  multi: any[];
  view: any[] = [700, 400];

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


  vehicleList: Vehicle[] = [];

  constructor() {}

  ngOnInit(): void {
    this.multi = [
      {
        "name": "United Kingdom",
        "series": [
          {
            "name": "2010",
            "value": 36240,
            "extra": {
              "code": "uk"
            }
          },
          {
            "name": "2000",
            "value": 32543,
            "extra": {
              "code": "uk"
            }
          },
          {
            "name": "1990",
            "value": 26424,
            "extra": {
              "code": "uk"
            }
          }
        ]
      },
      {
        "name": "Kuwait",
        "series": [
          {
            "name": "1990",
            "value": 28819
          },
          {
            "name": "2000",
            "value": 38345
          },
          {
            "name": "2010",
            "value": 44274
          }
        ]
      },
      {
        "name": "Switzerland",
        "series": [
          {
            "name": "1990",
            "value": 11356
          },
          {
            "name": "2000",
            "value": 45329
          },
          {
            "name": "2010",
            "value": 44744
          }
        ]
      }
    ]

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
