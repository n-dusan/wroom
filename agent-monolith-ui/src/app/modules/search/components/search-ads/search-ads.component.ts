import { Component, OnInit } from '@angular/core';
import { Ad } from '../../model/ad.model';
import { AdService } from '../../service/ad.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-search-ads',
  templateUrl: './search-ads.component.html',
  styleUrls: ['./search-ads.component.css']
})
export class SearchAdsComponent implements OnInit {

  ads: Ad[] = [];

  constructor(private adService: AdService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    // this.adService.all().subscribe(
    //   data => {
    //     this.ads = data;
    //     console.log(data);
    //   },
    //   error => {
    //     this.toastr.error('There was an error!', 'Error')
    //   }
    // );

    this.ads.push(new Ad(1, 1, new Date(2020,5,15),new Date(2020,6,15), 120, true, 1, 'Bulevar oslobodjenja 8', true));
  }

}
