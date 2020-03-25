package vm.example.solarStatistic.model.solardb;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "hourly_production_data")
public class HourlyProductionData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "time_stamp", nullable = false)
    private LocalDateTime localDateTime;

    @Column(name = "electricity_produced_mw", nullable = false)
    private BigDecimal electricityProducedMW;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name = "solar_park_id", nullable = false)
    private SolarPark solarPark;


    public HourlyProductionData(LocalDateTime localDateTime, BigDecimal electricityProducedMW) {
        this.localDateTime = localDateTime;
        this.electricityProducedMW = electricityProducedMW;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public BigDecimal getElectricityProducedMW() {
        return electricityProducedMW;
    }

    public void setElectricityProducedMW(BigDecimal electricityProducedMW) {
        this.electricityProducedMW = electricityProducedMW;
    }

    public SolarPark getSolarPark() {
        return solarPark;
    }

    public void setSolarPark(SolarPark solarPark) {
        this.solarPark = solarPark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HourlyProductionData that = (HourlyProductionData) o;
        return Objects.equals(id, that.id) &&
                localDateTime.equals(that.localDateTime) &&
                electricityProducedMW.equals(that.electricityProducedMW) &&
                solarPark.equals(that.solarPark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localDateTime, electricityProducedMW, solarPark);
    }
}
