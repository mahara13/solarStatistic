package vm.example.solarStatistic.controller;

import org.springframework.web.bind.annotation.*;
import vm.example.solarStatistic.model.SolarParkResponse;
import vm.example.solarStatistic.model.StatisticRequest;
import vm.example.solarStatistic.model.StatisticResponse;
import vm.example.solarStatistic.service.SolarService;

@RestController
public class SolarController {

    private SolarService solarService;

    public SolarController(SolarService solarService) {
        this.solarService = solarService;
    }

    @PostMapping(value = "solar/{id}/statistic")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public StatisticResponse getStatistic(@RequestBody StatisticRequest request, @PathVariable(name = "id") Long solarParkId) throws Exception {
        return solarService.getHourlyProductionData(solarParkId, request.getStartDate(), request.getEndDate());
    }

    @GetMapping(value = "solar-parks")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public SolarParkResponse get() throws Exception {
        return new SolarParkResponse(solarService.getParks());
    }
}
