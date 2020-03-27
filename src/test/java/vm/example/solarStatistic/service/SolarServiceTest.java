package vm.example.solarStatistic.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import vm.example.solarStatistic.model.DayStatistic;
import vm.example.solarStatistic.model.StatisticResponse;
import vm.example.solarStatistic.model.exception.BadFormattedDateRangeException;
import vm.example.solarStatistic.repository.HourlyProductionDataRepository;
import vm.example.solarStatistic.repository.SolarParkRepository;
import vm.example.solarStatistic.util.db.EmbeddedDB;
import vm.example.solarStatistic.util.db.EmbeddedDatabaseConfiguration;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmbeddedDatabaseConfiguration.class})
@TestInstance(PER_CLASS)
@ActiveProfiles("test")
class SolarServiceTest {

    @Autowired
    private HourlyProductionDataRepository hourlyProductionDataRepository;
    @Autowired
    private SolarParkRepository solarParkRepository;
    @Autowired
    private SolarService solarService;

    private EmbeddedDB embeddedDB;

    @BeforeAll
    private void init() {
        embeddedDB = new EmbeddedDB(solarParkRepository, hourlyProductionDataRepository);
    }

    @Test
    void getCorrectDateIfTimeRangeIsCorrect() throws Exception {
        StatisticResponse statisticResponse = solarService.getHourlyProductionData(1L, embeddedDB.march1, embeddedDB.march2);
        assertTrue(statisticResponse != null && statisticResponse.getList()  != null && statisticResponse.getList().size() > 0);
    }

    @Test
    void getCorrectDateIfTimeRangeIsOneDate() throws Exception {
        StatisticResponse statisticResponse = solarService.getHourlyProductionData(1L, embeddedDB.march1, embeddedDB.march1);
        assertTrue(statisticResponse != null && statisticResponse.getList()  != null && statisticResponse.getList().size() > 0);
    }

    @Test
    void getExceptionIfNotCorrectTimeRange() throws Exception {
        assertThrows(BadFormattedDateRangeException.class, () -> solarService.getHourlyProductionData(1L, embeddedDB.march1, embeddedDB.february29));
    }

    @Test
    void capacityCalculationInCorrectTimeRange() throws Exception {
        StatisticResponse statisticResponse = solarService.getHourlyProductionData(1L, embeddedDB.february29, embeddedDB.march3);
        assertTrue(statisticResponse != null && statisticResponse.getList() != null && statisticResponse.getList().size() == 2);
        DayStatistic firstDayStatistic = statisticResponse.getList().get(0);
        DayStatistic secondDayStatistic = statisticResponse.getList().get(1);
        checkDayStatistic(firstDayStatistic,embeddedDB.february29.toLocalDate(), 0.5D, 2);
        checkDayStatistic(secondDayStatistic,embeddedDB.march1.toLocalDate(), 1D, 1);
    }

    private void checkDayStatistic(DayStatistic dayStatistic, LocalDate localDate, Double capacityFactor, Integer countOfProductiveHours) {
        assertEquals(dayStatistic.getCapacityFactor().doubleValue(), capacityFactor);
        assertEquals(dayStatistic.getDay(), localDate);
        assertEquals(dayStatistic.getCountOfProductiveHours(), countOfProductiveHours);
    }
}
