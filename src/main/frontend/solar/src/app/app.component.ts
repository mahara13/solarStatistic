import {Component, OnInit} from '@angular/core';
import {SolarStatisticService} from "./solar-statistic.service";
import {Observable} from "rxjs";
import {SolarPark, SolarParksService} from "./solar-parks.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  ngOnInit(): void {
    this.solarPlants = this.solarParksService.getSolarParks()
  }

  public solarPlants: Observable<SolarPark[]>;

  constructor(
    public solarStatisticService: SolarStatisticService,
    public solarParksService:SolarParksService) {
  }
}
