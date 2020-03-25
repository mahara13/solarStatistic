package vm.example.solarStatistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vm.example.solarStatistic.model.solardb.HourlyProductionData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HourlyProductionDataRepository extends JpaRepository<HourlyProductionData, Long> {


    @Query(value = "SELECT * FROM (\n" +
            "                  SELECT id, solar_park_id, timezone(:park_time_zone, time_stamp) as time_stamp, electricity_produced_mw\n" +
            "                  FROM hourly_production_data\n" +
            "                  WHERE solar_park_id = :park_id\n" +
            "              ) as converted_to_time_zone\n" +
            "WHERE converted_to_time_zone.time_stamp >= :start_date and converted_to_time_zone.time_stamp <= :end_date",
            nativeQuery = true)
    List<HourlyProductionData> getHourlyProductionDataBySolarParkAndTransformToTimeZoneOfSolarParkAndInsideTimeRange(
            @Param("park_id") Long solarParkId,
            @Param("park_time_zone") String solarParkTimeZone,
            @Param("start_date") LocalDateTime startDate,
            @Param("end_date") LocalDateTime endDate);
}
