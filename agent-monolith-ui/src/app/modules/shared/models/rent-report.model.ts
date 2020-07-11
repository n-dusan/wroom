export class RentReport {
  public constructor(
    public id?: number,
    public traveledMiles?: number,
    public note?: string,
    public rentRequestId?: number,
    public dateReport?: Date
) { }
}
