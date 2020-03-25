package vm.example.solarStatistic.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import vm.example.solarStatistic.model.solardb.HourlyProductionData;
import vm.example.solarStatistic.util.db.EmbeddedDB;
import vm.example.solarStatistic.util.db.EmbeddedDatabaseConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmbeddedDatabaseConfiguration.class})
@TestInstance(PER_CLASS)
@ActiveProfiles("test")
class HourlyProductionDataRepositoryTest {
    @Autowired
    private HourlyProductionDataRepository hourlyProductionDataRepository;
    @Autowired
    private SolarParkRepository solarParkRepository;
    private EmbeddedDB embeddedDB;

    @BeforeAll
    private void init() {
        embeddedDB = new EmbeddedDB(solarParkRepository, hourlyProductionDataRepository);
    }

    @Test
    void getEmptyListIfNoDataInRange() {
        List<HourlyProductionData> list = hourlyProductionDataRepository
                .getHourlyProductionDataBySolarParkAndTransformToTimeZoneOfSolarParkAndInsideTimeRange(
                        embeddedDB.greenwichSolarPark.getId(),
                        embeddedDB.greenwichSolarPark.getTimeZone(),
                        embeddedDB.january1,
                        embeddedDB.february29);

        assertTrue(list != null && list.size() == 0);
    }


    @Test
    void getDataInRangeOfOneDay() {
        List<HourlyProductionData> list = hourlyProductionDataRepository
                .getHourlyProductionDataBySolarParkAndTransformToTimeZoneOfSolarParkAndInsideTimeRange(
                        embeddedDB.greenwichSolarPark.getId(),
                        embeddedDB.greenwichSolarPark.getTimeZone(),
                        embeddedDB.march1,
                        embeddedDB.march1_end);

        assertTrue(list != null && list.size() == 1);
        HourlyProductionData hourlyProductionData = list.get(0);
        assertEquals(hourlyProductionData.getElectricityProducedMW().doubleValue(), embeddedDB.march1_hour1_greenwich.getElectricityProducedMW().doubleValue());
        assertEquals(hourlyProductionData.getLocalDateTime(), embeddedDB.march1_hour1_greenwich.getLocalDateTime());
    }

    @Test
    void getFilteredByTimeZone() {
        //Japan time zone has shift therefore hours for March 1 in UTC for Japan factory actually will happened on February 29
        List<HourlyProductionData> list = hourlyProductionDataRepository
                .getHourlyProductionDataBySolarParkAndTransformToTimeZoneOfSolarParkAndInsideTimeRange(
                        embeddedDB.japanSolarPark.getId(),
                        embeddedDB.japanSolarPark.getTimeZone(),
                        embeddedDB.march1,
                        embeddedDB.march1_end);

        assertTrue(list != null && list.size() == 1);
        HourlyProductionData hourlyProductionData = list.get(0);
        assertEquals(hourlyProductionData.getElectricityProducedMW().doubleValue(), embeddedDB.march2_hour1_japan.getElectricityProducedMW().doubleValue());
    }

    @Test
    void getNoDataIfTheEdgeOfTheRange() {
        List<HourlyProductionData> list = hourlyProductionDataRepository
                .getHourlyProductionDataBySolarParkAndTransformToTimeZoneOfSolarParkAndInsideTimeRange(
                        embeddedDB.greenwichSolarPark.getId(),
                        embeddedDB.greenwichSolarPark.getTimeZone(),
                        embeddedDB.february29,
                        embeddedDB.february29_end);

        assertTrue(list != null && list.size() == 0);
    }
}
