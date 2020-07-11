import { Ad } from './ad.model';

export class Debt {
    id:number;
    miles: number;
    priceListId: number;
    rentRequestId: number;
    adObj: Ad;

    constructor(miles?: number, priceListId?: number, rentRequestId?: number, adObj?: Ad){
        this.miles = miles;
        this.priceListId = priceListId;
        this.rentRequestId = rentRequestId;
        this.adObj = adObj;
    }

}