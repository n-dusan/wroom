import { Component, OnInit, ViewChild } from '@angular/core';
import { PriceList } from 'src/app/modules/shared/models/price-list.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { PriceListService } from '../../services/price-list.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-price-list-select',
  templateUrl: './price-list-select.component.html',
  styleUrls: ['./price-list-select.component.css']
})
export class PriceListSelectComponent implements OnInit {

  private ngUnsubscribe = new Subject();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  isLoadingResults: boolean = true;
  displayedColumns: string[] = ['select', 'price per day', 'price per mile', 'collision damage waiver', 'discount %'];

  dataSource: MatTableDataSource<PriceList> = new MatTableDataSource;

  selectedPriceList: string = '';

  constructor(
    private priceListService: PriceListService,
    public dialogRef: MatDialogRef<any>,
  ) { }


  ngOnInit(): void {

    this.dataSource.data = [];
    this.fetch();
    this.dataSource.paginator = this.paginator;

  }

  fetch() {
    this.priceListService.findAllActive().pipe(takeUntil(this.ngUnsubscribe)).subscribe((result: PriceList[]) => {
      console.log('all', result)
      this.dataSource.data = result;
    })
  }

  onSelectChange(row: any) {
    //todo: on row selected, forward the data to the create-ad component (via dialog close data)
    console.log('wat i got', row)
    this.dialogRef.close(row);
  }

}
