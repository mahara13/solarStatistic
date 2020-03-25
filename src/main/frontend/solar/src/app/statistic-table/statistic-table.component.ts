import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable} from '@angular/material/table';
import {StatisticTableDataSource} from './statistic-table-datasource';
import {SolarStatisticService, StatisticTableItem} from "../solar-statistic.service";


@Component({
  selector: 'app-statistic-table',
  templateUrl: './statistic-table.component.html',
  styleUrls: ['./statistic-table.component.css']
})
export class StatisticTableComponent implements AfterViewInit, OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<StatisticTableItem>;
  dataSource: StatisticTableDataSource;

  displayedColumns = ['date', 'capacityFactor', 'countOfProductiveHours'];

  constructor(private solarStatisticService: SolarStatisticService) { }

  ngOnInit() {
    this.dataSource = new StatisticTableDataSource(this.solarStatisticService.statistic$);
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }
}
