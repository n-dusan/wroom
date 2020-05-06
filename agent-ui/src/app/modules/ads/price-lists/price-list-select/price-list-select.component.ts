import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PriceList } from 'src/app/modules/shared/models/price-list.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-price-list-select',
  templateUrl: './price-list-select.component.html',
  styleUrls: ['./price-list-select.component.css']
})
export class PriceListSelectComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  isLoadingResults: boolean = true;
  displayedColumns: string[] = ['select', 'price per day', 'price per mile', 'collision damage waiver', 'discount %'];

  dataSource: MatTableDataSource<PriceList> = new MatTableDataSource;

  selectedPriceList: string = '';

  constructor() { }


  ngOnInit(): void {

    this.dataSource.data = [
      {
        pricePerMile: 32,
        pricePerDay: 2,
        priceCDW: 55,
        discount: 55
      }
    ];
    this.dataSource.paginator = this.paginator;

  }

  onSelectChange(row: any) {
    console.log('wat i got', row)
  }

}
