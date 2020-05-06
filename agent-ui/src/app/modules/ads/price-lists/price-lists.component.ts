import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

import { PriceList } from '../../shared/models/price-list.model'
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from './dialog/dialog.component';
import { PriceListSelectComponent } from './price-list-select/price-list-select.component';

@Component({
  selector: 'app-price-lists',
  templateUrl: './price-lists.component.html',
  styleUrls: ['./price-lists.component.css']
})
export class PriceListsComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  isLoadingResults: boolean = true;
  displayedColumns: string[] = ['price per day', 'price per mile', 'collision damage waiver', 'discount %', 'edit', 'delete'];

  dataSource: MatTableDataSource<PriceList> = new MatTableDataSource;

  constructor(private dialog: MatDialog) { }

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

  onAddPriceListClick() {

    let dialogRef = this.dialog.open(DialogComponent, {
      data: {
        type: 'create'
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }


  onEditPriceListClick() {

    let dialogRef = this.dialog.open(DialogComponent, {
      data: {
        type: 'edit'
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  openSelect() {

    let dialogRef = this.dialog.open(PriceListSelectComponent);
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

}
