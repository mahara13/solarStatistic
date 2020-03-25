import {DataSource} from '@angular/cdk/collections';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {map} from 'rxjs/operators';
import {merge, Observable} from 'rxjs';
import {StatisticTableItem} from "../solar-statistic.service";

/**
 * Data source for the StatisticTable view. This class should
 * encapsulate all logic for fetching and manipulating the displayed data
 * (including sorting, pagination, and filtering).
 */
export class StatisticTableDataSource extends DataSource<StatisticTableItem> {
  data: StatisticTableItem[] = [];
  paginator: MatPaginator;
  sort: MatSort;
  private readonly _observable:Observable<StatisticTableItem[]>;

  constructor(observable:Observable<StatisticTableItem[]>) {
    super();
    this._observable = observable;
  }

  /**
   * Connect this data source to the table. The table will only update when
   * the returned stream emits new items.
   * @returns A stream of the items to be rendered.
   */
  connect(): Observable<StatisticTableItem[]> {
    // Combine everything that affects the rendered data into one update
    // stream for the data-table to consume.
    const dataMutations = [
      (this._observable),
      this.paginator.page,
      this.sort.sortChange
    ];

    return merge(...dataMutations).pipe(map((value, index) => {
      if (value instanceof Array) {
        this.data = value;
      }

      return this.getPagedData(this.getSortedData([...this.data]));
    }));
  }

  /**
   *  Called when the table is being destroyed. Use this function, to clean up
   * any open connections or free any held resources that were set up during connect.
   */
  disconnect() {}

  /**
   * Paginate the data (client-side). If you're using server-side pagination,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getPagedData(data: StatisticTableItem[]) {
    const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
    return data.splice(startIndex, this.paginator.pageSize);
  }

  /**
   * Sort the data (client-side). If you're using server-side sorting,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getSortedData(data: StatisticTableItem[]) {
    if (!this.sort.active || this.sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      const isAsc = this.sort.direction === 'asc';
      switch (this.sort.active) {
        case 'date': return compare(new Date(a.day).getTime(), new Date(b.day).getTime(), isAsc);//Could be improved, we need find why date type is converted to string type
        case 'capacityFactor': return compare(+a.capacityFactor, +b.capacityFactor, isAsc);
        case 'countOfProductiveHours': return compare(+a.countOfProductiveHours, +b.countOfProductiveHours, isAsc);
        default: return 0;
      }
    });
  }
}

/** Simple sort comparator for example ID/Name columns (for client-side sorting). */
function compare(a: string | number, b: string | number, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
