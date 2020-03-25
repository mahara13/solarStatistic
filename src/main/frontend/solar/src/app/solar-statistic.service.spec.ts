import { TestBed } from '@angular/core/testing';

import { SolarStatisticService } from './solar-statistic.service';

describe('SolarStatisticService', () => {
  let service: SolarStatisticService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SolarStatisticService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
