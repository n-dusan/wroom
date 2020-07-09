import { PriceList } from './price-list.model';
import { Ad } from './ad.model';

export class Debt {
    id:number;
    miles: number;
    priceListId: number;
    rentRequestId: number;
    priceListObj: PriceList;
    adObj: Ad;

    constructor(miles?: number, priceListId?: number, rentRequestId?: number, priceListObj?: PriceList,
        adObj?: Ad){
        this.miles = miles;
        this.priceListId = priceListId;
        this.rentRequestId = rentRequestId;
        this.priceListObj = priceListObj;
        this.adObj = adObj;
    }

}