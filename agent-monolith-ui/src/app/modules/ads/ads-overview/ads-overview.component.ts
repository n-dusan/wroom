import { Component, OnInit, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Ad } from '../model/ad.model';
import { MatDialog } from '@angular/material/dialog';
import { PriceListService } from '../services/price-list.service';
import { AdsService } from '../services/ads.service';
import { takeUntil } from 'rxjs/operators';
import { AuthService } from '../../auth/service/auth.service';
import { LoggedUser } from '../../auth/model/logged-user.model';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-ads-overview',
  templateUrl: './ads-overview.component.html',
  styleUrls: ['./ads-overview.component.css']
})
export class AdsOverviewComponent implements OnInit {

  private ngUnsubscribe = new Subject();
  loggedUser: LoggedUser;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  isLoadingResults: boolean = true;
  displayedColumns: string[] = ['from', 'to', 'mile-limit', 'address', 'vehicle', 'price-list', 'location', 'gps', 'edit', 'delete'];

  dataSource: MatTableDataSource<Ad> = new MatTableDataSource;

  constructor(
    private dialog: MatDialog,
    private priceListService: PriceListService,
    private adsService: AdsService,
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.authService.whoami().subscribe(data => {
        this.loggedUser = data;
        this.dataSource.data = [];
        this.refresh();
        this.dataSource.paginator = this.paginator;
      },
      error => {
        console.log(error)
      }

    );
  }

  refresh() {
    this.adsService.getAllActiveForUser(this.loggedUser.id).pipe(takeUntil(this.ngUnsubscribe)).subscribe((data: Ad[]) => {
      this.dataSource.data = data;
      console.log('my data', data)
    })
  }

  onAddAdClick() {
    this.router.navigate(['../new'], { relativeTo: this.activatedRoute });
  }

  onEditAdClick(ad: Ad) {
    console.log('edit ad', ad)
  }

  onDeleteAdClick(ad: Ad) {
    console.log('delete ad', ad)
    this.adsService.deleteAd(ad.id).subscribe(response => {
      this.refresh()
    })
  }


}