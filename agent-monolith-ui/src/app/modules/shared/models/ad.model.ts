import { Vehicle } from './vehicle.model';
import { PriceList } from './price-list.model';
import { AdLocation } from './ad-location.model';

export class Ad {
    constructor(
      public id?: number,
      public vehicleId?: number,
      public priceListId?: number,
      public availableFrom?: Date,
      public availableTo?: Date,
      public mileLimit?: number,
      public mileLimitEnabled?: boolean,
      public locationId?: number,
      public address?: string,
      public gps?: boolean,
      public vehicleObj?: Vehicle,
      public locationObj?: AdLocation,
      public priceListObj?: PriceList,
      public image?: string
    ) {}
  }