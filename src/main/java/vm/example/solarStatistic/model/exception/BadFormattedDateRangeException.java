package vm.example.solarStatistic.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

public class BadFormattedDateRangeException extends ResponseStatusException {
    public BadFormattedDateRangeException(LocalDateTime startDate, LocalDateTime endDate) {
        super(HttpStatus.BAD_REQUEST, getReason(startDate, endDate));
    }

    private static String getReason(LocalDateTime startDate, LocalDateTime endDate) {
        return new StringBuilder("Bad formatted date range  start date '")
                .append(startDate).append("' should be before end date '").append(endDate).append("'").toString();
    }
}
