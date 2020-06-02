import { Ad } from '../../ads/model/ad.model';

export class RentRequest{
    id:number;
    status: String;
    fromDate:Date;
    toDate: Date;
    ad: Ad;

    constructor(status?: String, fromDate?: Date, toDate?: Date, ad?: Ad){
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.ad = ad;
    }
}