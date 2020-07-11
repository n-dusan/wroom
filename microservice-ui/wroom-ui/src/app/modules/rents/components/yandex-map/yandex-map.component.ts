import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ILoadEvent } from 'angular8-yandex-maps';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { environment } from 'src/environments/environment';


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
  private stompClient;

  geometry: number[] = [44.665446, 20.191443]
  point: Point = new Point(44.665446, 20.191443);
  points: Point[] = [];

  constructor(private _changeDetectorRef: ChangeDetectorRef) { }

  public onLoad(event: ILoadEvent) {
    this._map = event.instance;
  }

  ngOnInit(): void {

    const serverUrl = environment.gpsSocketService;

    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    this.stompClient.connect({}, () => {
      that.stompClient.subscribe('/gps', (message) => {
        if (message.body) {
         let parsedMessage = JSON.parse(message.body);

         that.point = new Point(parsedMessage.latitude,  parsedMessage.longitude);
         that.points.push(new Point(this.point.lat, this.point.long));
        //  that._map.panTo([this.point.lat, this.point.long])
         that._changeDetectorRef.detectChanges();
        }
      });
    });
  }
}
