import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError, map} from "rxjs/operators";
import {BaseSolarService} from "./base-solar.service";

@Injectable({
  providedIn: 'root'
})
export class SolarParksService extends BaseSolarService{
  private solarParksEndpoint = 'http://localhost:8080/solar-parks';

  constructor(private http: HttpClient) {
    super();
  }

  public getSolarParks(): Observable<SolarPark[]> {
    return this.http
      .get<SolarParkResponse>(this.solarParksEndpoint, this.getOptions())
      .pipe(
        catchError(this.handleError),
        map(res => res.list)
      );
  }
}

export interface SolarParkResponse {
  list: Array<SolarPark>;
}

export interface SolarPark {
  id: number;
  name: string;
  capacityPerHourMW: number;
  timeZone: string;
}
