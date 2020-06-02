import { Ad } from '../../ads/model/ad.model';

export class RentRequest{
    id:number;
    status: String;
    fromDate:Date;
    toDate: Date;
    ads: Ad[] = [];

    constructor(status?: String, fromDate?: Date, toDate?: Date, ads?: Ad[]){
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.ads = ads;
    }
}