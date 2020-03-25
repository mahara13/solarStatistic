import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {merge, Observable, of, Subject} from "rxjs";
import {catchError, map, mergeMap} from "rxjs/operators";
import {BaseSolarService} from "./base-solar.service";

@Injectable({
  providedIn: 'root'
})
export class SolarStatisticService extends BaseSolarService{
  private statisticEndpoint = 'http://localhost:8080/solar/{id}/statistic';

  idSubject: Subject<number> = new Subject<number>();
  startDateSubject: Subject<Date> = new Subject<Date>();
  endDateSubject: Subject<Date> = new Subject<Date>();

  id: number;
  startDate: Date;
  endDate: Date;

  isIdInvalid:boolean = true;
  isStartDateInvalid: boolean = true;
  isEndDateInvalid: boolean = true;
  showIncorrectDataRangeMessage: boolean = false;

  statisticCashMap:Map<string, StatisticTableItem[]> = new Map<string, StatisticTableItem[]>();
  statisticCashMapMaxCapacity: number = 100;

  constructor(private http: HttpClient) {
    super();
    this.statistic$.subscribe( value => {
      this.updateStatisticCash(value);
    })
  }

  private updateStatisticCash(value: StatisticTableItem[]):void {
    if (this.isStatisticInputDataCorrect()) {
      let currentStatisticKey = this.getCurrentStatisticKey();
      if (!this.statisticCashMap.has(currentStatisticKey)) {
        this.statisticCashMap.set(currentStatisticKey, value);
      }
    }

    if (this.statisticCashMap.size > this.statisticCashMapMaxCapacity) {
      let count: number = this.statisticCashMap.size - this.statisticCashMapMaxCapacity;
      for (let key of this.statisticCashMap.keys()) {
        this.statisticCashMap.delete(key);
        count -= 1;
        if (count <= 0) {
          break;
        }
      }
    }
  }

  private getCurrentStatisticKey(): string {
    return this.getStatisticKey(this.id, this.startDate, this.endDate);
  }

  private getStatisticKey(aId:number, aStartDate:Date, aEndDate:Date): string {
    return aId.toString() + aStartDate.toString() + aEndDate.toString();
  }

  private getStatisticFromServer(id: number, startDate: Date, endDate: Date): Observable<StatisticTableItem[]> {
    return this.http
      .post<StatisticResponse>(
        this.statisticEndpoint.replace('{id}', String(id)),
        {startDate: startDate, endDate: endDate},
        {headers: {"Access-Control-Allow-Origin": "*"}}
      ).pipe(
        catchError(this.handleError),
        map(res => res.list)
      );
  }

  private getStatistic$(): Observable<StatisticTableItem[]> {
    if (this.isStatisticInputDataCorrect()) {
      let currentStatisticKey = this.getCurrentStatisticKey();
      return this.statisticCashMap.has(currentStatisticKey) ? of(this.statisticCashMap.get(currentStatisticKey)) : this.getStatisticFromServer(this.id, this.startDate, this.endDate);
    } else {
      return of([]);
    }
  }

  private isStatisticInputDataCorrect(): boolean {
    return !this.isIdInvalid &&
      !this.isStartDateInvalid &&
      !this.isEndDateInvalid &&
      !this.showIncorrectDataRangeMessage;
  }

  public statistic$: Observable<StatisticTableItem[]> = merge(
    this.idSubject.asObservable().pipe(mergeMap(newId => {
      return this.getStatistic$();
    })),
    this.startDateSubject.asObservable().pipe(mergeMap(newStartDate => {
      return this.getStatistic$();
    })),
    this.endDateSubject.asObservable().pipe(mergeMap(newEndDate => {
      return this.getStatistic$();
    })),
  )

  public setId( newId:number) {
    if (this.id != newId) {
      this.id = newId;
      this.idSubject.next(this.id);
    }

    this.isIdInvalid = isNaN(this.id);
  }

  public trySetStartDate(date:Date) {
    this.startDate = date;
    this.isStartDateInvalid = this.startDate == null;
    this.updateShowIncorrectDataRangeMessage();
    this.startDateSubject.next(this.startDate);
  }

  private updateShowIncorrectDataRangeMessage() {
    this.showIncorrectDataRangeMessage = this.startDate != null && this.startDate != null
      && this.endDate < this.startDate ;
  }

  public trySetEndDate(date:Date) {
    this.endDate = date;
    this.isEndDateInvalid = this.endDate == null;
    this.updateShowIncorrectDataRangeMessage();
    this.endDateSubject.next(this.endDate);
  }
}

export interface StatisticResponse {
  list: Array<StatisticTableItem>;
}

export interface StatisticTableItem {
  day: Date;
  capacityFactor: number;
  countOfProductiveHours: number;
}
