import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BaseSolarService {

  constructor() { }

  protected handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `with message: ${error.error.message}`);
    }

    return of({list:[]});
  };

  protected getOptions() {
    return {headers: {"Access-Control-Allow-Origin": "*"}};
  }
}
