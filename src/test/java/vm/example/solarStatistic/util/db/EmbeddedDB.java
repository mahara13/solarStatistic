package vm.example.solarStatistic.util.db;

import vm.example.solarStatistic.model.solardb.HourlyProductionData;
import vm.example.solarStatistic.model.solardb.SolarPark;
import vm.example.solarStatistic.repository.HourlyProductionDataRepository;
import vm.example.solarStatistic.repository.SolarParkRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EmbeddedDB {
    public SolarPark japanSolarPark;
    public LocalDateTime february29;
    public LocalDateTime march3;
    public SolarPark easternEuropeSolarPark;
    public SolarPark greenwichSolarPark;
    public LocalDateTime january1;
    public LocalDateTime march1;
    public LocalDateTime march2;
    public LocalDateTime march1_end;
    public LocalDateTime february29_end;
    public HourlyProductionData march1_hour1_greenwich;
    public HourlyProductionData march1_hour1_japan;
    public HourlyProductionData march1_hour2_japan;
    public HourlyProductionData march2_hour1_japan;

    public EmbeddedDB(SolarParkRepository solarParkRepository,
                      HourlyProductionDataRepository hourlyProductionDataRepository) {
        japanSolarPark = new SolarPark("Japan", new BigDecimal(10), "JST");
        easternEuropeSolarPark = new SolarPark("Eastern Europe", new BigDecimal(20), "EST");
        greenwichSolarPark = new SolarPark("Greenwich", new BigDecimal(30), "GMT");
        solarParkRepository.save(japanSolarPark);
        solarParkRepository.save(easternEuropeSolarPark);
        solarParkRepository.save(greenwichSolarPark);


        march1 = LocalDateTime.of(2020, 03, 1, 0, 0);
        march1_end = march1.plusDays(1).minusSeconds(1);
        march2 = LocalDateTime.of(2020, 03, 2, 0, 0);

        march1_hour1_japan = getHourlyProductionData(march1, japanSolarPark, 5D);
        march1_hour2_japan = getHourlyProductionData(march1, japanSolarPark, 1D);
        march2_hour1_japan = getHourlyProductionData(march2, japanSolarPark, 10D);

        hourlyProductionDataRepository.save(march1_hour1_japan);
        hourlyProductionDataRepository.save(march1_hour2_japan);
        hourlyProductionDataRepository.save(march2_hour1_japan);

        march1_hour1_greenwich = getHourlyProductionData(march1, greenwichSolarPark, 30D);
        hourlyProductionDataRepository.save(march1_hour1_greenwich);

        solarParkRepository.flush();
        hourlyProductionDataRepository.flush();

        january1 = LocalDateTime.of(2020, 01, 1, 0, 0);
        february29 = LocalDateTime.of(2020, 02, 29, 0, 0);
        february29_end = february29.plusDays(1).minusSeconds(1);
        march3 = LocalDateTime.of(2020, 03, 3, 0, 0);
    }

    private HourlyProductionData getHourlyProductionData(LocalDateTime dateTime, SolarPark solarPark, Double producedMW) {
        HourlyProductionData march1_hour1 = new HourlyProductionData(dateTime, new BigDecimal(producedMW));
        march1_hour1.setSolarPark(solarPark);
        return march1_hour1;
    }
}
