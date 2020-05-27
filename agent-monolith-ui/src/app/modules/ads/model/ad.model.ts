export class Ad {
  constructor(
    public vehicleId?: number,
    public priceListId?: number,
    public availableFrom?: Date,
    public availableTo?: Date,
    public mileLimit?: number,
    public mileLimitEnabled?: boolean,
    public locationId?: string,
    public address?: string,
    public gps?: boolean
  ) {}
}
