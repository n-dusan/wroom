import { Component, OnInit } from '@angular/core';
import { RentReportService } from 'src/app/modules/rents/services/rent-report.service';
import { Debt } from '../../models/debt.model';
import { AdsService } from 'src/app/modules/ads/services/ads.service';
import { Ad } from 'src/app/modules/ads/model/ad.model';
import { AuthService } from 'src/app/modules/auth/service/auth.service';
import { LoggedUser } from 'src/app/modules/auth/model/logged-user.model';
import { RentsService } from 'src/app/modules/rents/services/rents.service';
import { RentRequest } from '../../models/rent-request.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-debts',
  templateUrl: './debts.component.html',
  styleUrls: ['./debts.component.css']
})
export class DebtsComponent implements OnInit {
  debts: Debt[] = [];
  listAds: Ad[] = [];
  loggedUser: LoggedUser;
  requestsList: any[] = [];
  ad: Ad;
  constructor(private rentReportService: RentReportService, private adsService: AdsService,
    private authService: AuthService, private rentsService: RentsService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.loadAds();
    this.loadDebts();
    this.loadRequests();
  }

  loadAds() {
    this.authService.whoami().subscribe(data => {
      this.loggedUser = data;
      console.log(this.loggedUser)
      this.adsService.getAllActiveForUser(this.loggedUser.id).subscribe(
        data => {
          this.listAds = data;
          console.log(this.listAds)
        }
      )

    },
      error => {
        console.log(error)
      }

    );
  }

  loadDebts() {
    this.rentReportService.alldebts().subscribe(
      data => {
        this.debts = data;
      }
    )
  }

  loadRequests() {
    this.rentsService.getAll().subscribe(
      data => {
        this.requestsList = data;
      }
    )
  }

  showMileLimit(debt: Debt) {

    const request = this.requestsList.find(x => x.id == debt.rentRequestId);
    const ad = this.listAds.find(x => x.id == request.ad.id);

    return this.ad?.mileLimit;
  }

  showAd(debt: Debt) {
    
    const request = this.requestsList.find(x => x.id == debt.rentRequestId);
    const ad = this.listAds.find(x => x.id == request.ad.id);
    return this.ad?.address;
  }

  payDebt(id: number) {
    this.rentReportService.payDebt(id).subscribe(
      data => {
        this.toastr.success('Successfully paid', 'Success');
      },
      error => {
        this.toastr.error('Error!', 'Error');
      }
    )
  }

}
