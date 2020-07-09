import { Component, OnInit } from '@angular/core';
import { PriceList } from '../../models/price-list.model';
import { LoggedUser } from 'src/app/modules/auth/model/logged-user.model';
import { AuthService } from 'src/app/modules/auth/service/auth.service';
import { ToastrService } from 'ngx-toastr';
import { AdsService } from 'src/app/modules/ads/services/ads.service';
import { RentReportService } from 'src/app/modules/rents/services/rent-report.service';
import { PriceListService } from 'src/app/modules/ads/services/price-list.service';
import { Debt } from '../../models/debt.model';
import { Ad } from 'src/app/modules/ads/model/ad.model';
import { RentsService } from 'src/app/modules/rents/services/rents.service';
import { RentRequest } from '../../models/rent-request.model';

@Component({
  selector: 'app-debts',
  templateUrl: './debts.component.html',
  styleUrls: ['./debts.component.css']
})
export class DebtsComponent implements OnInit {

  debts: Debt[] = [];
  listAds: Ad[] = [];
  priceLists: PriceList[] = [];
  loggedUser: LoggedUser;
  requestsList: RentRequest[] = [];
  ad: Ad;
  constructor(private rentReportService: RentReportService, private adsService: AdsService,
    private authService: AuthService, private rentsService: RentsService,
    private toastr: ToastrService, private priceListService: PriceListService) { }

  ngOnInit(): void {
    this.loadAds();
    this.loadDebts();
    this.loadRequests();
    this.loadPriceLists();
  }

  loadAds() {
    this.authService.whoami().subscribe(data => {
      this.loggedUser = data;
      // console.log(this.loggedUser)
      this.adsService.getAllActiveForUser(this.loggedUser.id).subscribe(
        data => {
          this.listAds = data;
          console.log('ADS', this.listAds)
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
        console.log('DEBTS', this.debts);
      }
    )
  }

  loadRequests() {
    this.rentsService.getAll().subscribe(
      data => {
        this.requestsList = data;
        console.log('REQUESTS', this.requestsList);

        for(let debt of this.debts) {
          const rr: RentRequest = this.requestsList.find(x => {return x.id === debt.rentRequestId});
          if(rr != null) {
            debt.adObj = rr.ad;
          }
        }
      }
    )
  }

  loadPriceLists() {
    this.priceListService.findAllActive().subscribe(
      data => {
        this.priceLists = data;
        console.log('PRICELISTS',this.priceLists)
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

  showPrice(debt: Debt) {
    const price = this.priceLists.find(x => x.id == debt.priceListId);
    return debt.miles * price?.pricePerMile;
  }

}
