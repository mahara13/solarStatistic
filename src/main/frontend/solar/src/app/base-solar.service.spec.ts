import { TestBed } from '@angular/core/testing';

import { BaseSolarService } from './base-solar.service';

describe('BaseSolarService', () => {
  let service: BaseSolarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BaseSolarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
