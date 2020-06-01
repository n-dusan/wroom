import { Ad } from '../../ads/model/ad.model';

export class RentRequest{
    id:number;
    fromDate:Date;
    toDate: Date;
    ads: Ad[] = [];

    constructor(fromDate?: Date, toDate?: Date, ads?: Ad[]){
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.ads = ads;
    }
}