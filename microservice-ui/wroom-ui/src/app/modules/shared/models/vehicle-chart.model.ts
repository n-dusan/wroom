import { VehicleItemChart } from './vehicle-item-chart.model';

export class VehicleChart {
  public constructor(
    public name?: string,
    public series?: VehicleItemChart[]
  ) {}
}
