package vm.example.solarStatistic.model;

import lombok.Data;
import vm.example.solarStatistic.model.solardb.SolarPark;

import java.util.List;

@Data
public class SolarParkResponse {
    private List<SolarPark> list;

    public SolarParkResponse() { }

    public SolarParkResponse(List<SolarPark> list) {
        this.list = list;
    }

    public List<SolarPark> getList() {
        return list;
    }
}
