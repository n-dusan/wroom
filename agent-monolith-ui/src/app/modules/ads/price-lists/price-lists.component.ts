import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

import { PriceList } from '../../shared/models/price-list.model'
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from './dialog/dialog.component';
import { PriceListSelectComponent } from './price-list-select/price-list-select.component';

import { DialogRegimeEnum } from '../model/dialog.enum'
import { PriceListService } from '../services/price-list.service';

import { take, takeUntil } from 'rxjs/operators'
import { Subject } from 'rxjs';


@Component({
  selector: 'app-price-lists',
  templateUrl: './price-lists.component.html',
  styleUrls: ['./price-lists.component.css']
})
export class PriceListsComponent implements OnInit, OnDestroy {

  private ngUnsubscribe = new Subject();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  isLoadingResults: boolean = true;
  displayedColumns: string[] = ['price-per-day', 'price-per-mile', 'collision-damage-waiver', 'discount-%', 'edit', 'delete'];

  dataSource: MatTableDataSource<PriceList> = new MatTableDataSource;

  constructor(
    private dialog: MatDialog,
    private priceListService: PriceListService) { }

  ngOnInit(): void {
    this.dataSource.data = [];
    this.refresh()
    this.dataSource.paginator = this.paginator;
  }

  refresh() {
    this.priceListService.findAllActive().pipe(takeUntil(this.ngUnsubscribe)).subscribe((result: PriceList[]) => {
      this.dataSource.data = result;
    })
  }

  onAddPriceListClick() {
    let dialogRef = this.dialog.open(DialogComponent, {
      data: {
        type: DialogRegimeEnum.CREATE
      }
    });


    dialogRef.afterClosed().subscribe(result => {
      this.refresh()
    });
  }


  onEditPriceListClick(priceList: PriceList) {

    let dialogRef = this.dialog.open(DialogComponent, {
      data: {
        type: DialogRegimeEnum.EDIT,
        priceList: priceList
      }
    });

    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
      this.refresh()
    });
  }

  onDeletePriceListClick(priceList: PriceList) {
    this.priceListService.remove(priceList.id).subscribe( response => {
      this.refresh();
    })
  }

  openSelect() {

    let dialogRef = this.dialog.open(PriceListSelectComponent);
    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
    });
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
