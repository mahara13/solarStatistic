package vm.example.solarStatistic.model;

import lombok.Data;

import java.util.List;

@Data
public class StatisticResponse {
    private List<DayStatistic> list;

    public StatisticResponse() { }

    public StatisticResponse(List<DayStatistic> list) {
        this.list = list;
    }

    public List<DayStatistic> getList() {
        return list;
    }
}
