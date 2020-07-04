export class Debt {
    id:number;
    miles: number;
    priceListId: number;
    rentRequestId: number;

    constructor(miles?: number, priceListId?: number, rentRequestId?: number){
        this.miles = miles;
        this.priceListId = priceListId;
        this.rentRequestId = rentRequestId;
    }

}