package vm.example.solarStatistic.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DayStatistic {
    private LocalDate day;
    private Double capacityFactor;
    private Integer countOfProductiveHours;

    public DayStatistic(LocalDate day, Double capacityFactor, Integer countOfProductiveHours) {
        this.day = day;
        this.capacityFactor = capacityFactor;
        this.countOfProductiveHours = countOfProductiveHours;
    }

    public LocalDate getDay() {
        return day;
    }

    public Double getCapacityFactor() {
        return capacityFactor;
    }

    public Integer getCountOfProductiveHours() {
        return countOfProductiveHours;
    }
}
