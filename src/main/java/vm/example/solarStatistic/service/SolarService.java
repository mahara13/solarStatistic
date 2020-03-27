package vm.example.solarStatistic.service;

import org.springframework.stereotype.Component;
import vm.example.solarStatistic.model.DayStatistic;
import vm.example.solarStatistic.model.StatisticResponse;
import vm.example.solarStatistic.model.exception.BadFormattedDateRangeException;
import vm.example.solarStatistic.model.solardb.HourlyProductionData;
import vm.example.solarStatistic.model.solardb.SolarPark;
import vm.example.solarStatistic.repository.HourlyProductionDataRepository;
import vm.example.solarStatistic.repository.SolarParkRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class SolarService {
    private SolarParkRepository solarParkRepository;
    private HourlyProductionDataRepository hourlyProductionDataRepository;

    public SolarService(SolarParkRepository solarParkRepository, HourlyProductionDataRepository hourlyProductionDataRepository) {
        this.solarParkRepository = solarParkRepository;
        this.hourlyProductionDataRepository = hourlyProductionDataRepository;
    }

    public List<SolarPark> getParks() {
        return solarParkRepository.findAll();
    }

    public StatisticResponse getHourlyProductionData(Long solarParkId,
                                                     LocalDateTime startDate,
                                                     LocalDateTime endDate) throws Exception {
        if (startDate == null  || endDate == null) {
            throw new BadFormattedDateRangeException(startDate, endDate);
        }

        LocalDateTime paramStartDate = startDate.with(LocalTime.MIN);
        LocalDateTime paramEndDate = endDate.with(LocalTime.MIN);
        paramEndDate = paramEndDate.plusDays(1).minusSeconds(1);

        if (paramStartDate.isAfter(paramEndDate)) {
            throw new BadFormattedDateRangeException(startDate, endDate);
        }

        SolarPark solarPark = solarParkRepository.getSolarParkById(solarParkId);

        List<HourlyProductionData> list = hourlyProductionDataRepository.getHourlyProductionDataBySolarParkAndTransformToTimeZoneOfSolarParkAndInsideTimeRange(
                solarParkId,
                solarPark.getTimeZone(),
                paramStartDate,
                paramEndDate);

        Map<LocalDate, List<HourlyProductionData>> mapHourlyProductionDataToDays = new TreeMap<>();
        list.forEach(hourlyProductionData -> {
            LocalDate dayKey = hourlyProductionData.getLocalDateTime().toLocalDate();
            if (mapHourlyProductionDataToDays.get(dayKey) == null) {
                mapHourlyProductionDataToDays.put(dayKey, new LinkedList<>());
            }
            mapHourlyProductionDataToDays.get(dayKey).add(hourlyProductionData);
        });

        List<DayStatistic> daysStatisticList = new LinkedList<>();
        mapHourlyProductionDataToDays.forEach((localDate, hourlyProductionDataList) -> {
            Double producedMW = hourlyProductionDataList.stream().reduce(0D,
                    (aDouble, hourlyProductionData) -> aDouble + hourlyProductionData.getElectricityProducedMW().doubleValue(),
                    (left, right) -> left + right);
            int countOfProductiveHours = hourlyProductionDataList.size();
            Double capacity = producedMW / (countOfProductiveHours * solarPark.getCapacityPerHourMW().doubleValue());
            DayStatistic dayStatistic = new DayStatistic(localDate,capacity, countOfProductiveHours);
            daysStatisticList.add(dayStatistic);
        });

        return new StatisticResponse(daysStatisticList);
    }
}
