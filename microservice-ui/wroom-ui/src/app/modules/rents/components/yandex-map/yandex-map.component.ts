import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ILoadEvent } from 'angular8-yandex-maps';


class Point {
  constructor(
    public lat: number,
    public long: number
  ) {}

  public get feature() {
    return {
      geometry: {
        type: "Point",
        coordinates: [this.lat, this.long]
      }
    }
  }

  public get options() {
    return {
      draggable: false
    }
  }
}


@Component({
  selector: 'app-yandex-map',
  templateUrl: './yandex-map.component.html',
  styleUrls: ['./yandex-map.component.css']
})
export class YandexMapComponent implements OnInit {

  private _map;

  geometry: number[] = [44.665446, 20.191443]
  point: Point = new Point(44.665446, 20.191443);
  points: Point[] = [] ;

  constructor(private _changeDetectorRef: ChangeDetectorRef) { }

  public onLoad(event: ILoadEvent) {
    this._map = event.instance;
  }

  ngOnInit(): void {
    setInterval(()=> {
      console.log('my geom', this.geometry)

       this.point = new Point(this.point.lat + 0.01, this.point.long + 0.01);
       this.points.push(new Point(this.point.lat, this.point.long))
      //  this._map.setCenter([this.point.lat, this.point.long]);
       this._map.panTo([this.point.lat, this.point.long])
      this._changeDetectorRef.detectChanges();
    }, 5000)
  }

}
