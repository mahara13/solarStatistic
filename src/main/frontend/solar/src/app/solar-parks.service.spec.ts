import { TestBed } from '@angular/core/testing';

import { SolarParksService } from './solar-parks.service';

describe('SolarParksService', () => {
  let service: SolarParksService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SolarParksService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
