export class PriceList {

  public constructor(
    public id?: number,
    public pricePerDay?: number,
    public pricePerMile?: number,
    public discount?: number,
    public priceCDW?: number) {}
}
