package project.game.fun7.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.game.fun7.service.CustomerSupportService;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Service
public class CustomerSupportServiceImpl implements CustomerSupportService {

    private final Logger log = LoggerFactory.getLogger(CustomerSupportServiceImpl.class);

    @Value("${customer.support.timezone}")
    private String customerSupportTimeZone;

    @Value("${customer.support.opening.hour}")
    private int customerSupportOpeningHour;

    @Value("${customer.support.closing.hour}")
    private int customerSupportClosingHour;

    public boolean isEnabled(TimeZone timeZone) {

        log.info("Checking if customer support is available...");

        Instant instant = Instant.now();
        ZonedDateTime zoneCustomerSupport = instant.atZone(ZoneId.of(customerSupportTimeZone));

        int hourNow = zoneCustomerSupport.getHour();
        DayOfWeek dayOfWeek = zoneCustomerSupport.getDayOfWeek();

        return isBetweenWorkingHours(hourNow) && isWorkday(dayOfWeek);
    }

    private boolean isWorkday(DayOfWeek dayOfWeek) {
        return dayOfWeek.getValue() < 6;
    }

    private boolean isBetweenWorkingHours(int hour) {
        return customerSupportOpeningHour <= hour && hour < customerSupportClosingHour;
    }
}
