package vm.example.solarStatistic.model.solardb;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "solar_park")
@NoArgsConstructor
public class SolarPark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "capacity_per_hour_mw", nullable = false)
    private BigDecimal capacityPerHourMW;

    @Column(name = "time_zone", nullable = false)
    private String timeZone;

    @OneToMany(mappedBy = "solarPark", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HourlyProductionData> items;

    public SolarPark(String name, BigDecimal capacityPerHourMW, String timeZone) {
        this.name = name;
        this.capacityPerHourMW = capacityPerHourMW;
        this.timeZone = timeZone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCapacityPerHourMW() {
        return capacityPerHourMW;
    }

    public void setCapacityPerHourMW(BigDecimal capacityPerHourMW) {
        this.capacityPerHourMW = capacityPerHourMW;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolarPark solarPark = (SolarPark) o;
        return Objects.equals(id, solarPark.id) &&
                name.equals(solarPark.name) &&
                capacityPerHourMW.equals(solarPark.capacityPerHourMW) &&
                timeZone.equals(solarPark.timeZone) &&
                items.equals(solarPark.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacityPerHourMW, timeZone, items);
    }
}
