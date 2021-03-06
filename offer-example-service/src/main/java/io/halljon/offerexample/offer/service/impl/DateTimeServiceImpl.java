package io.halljon.offerexample.offer.service.impl;

import io.halljon.offerexample.offer.service.DateTimeService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class DateTimeServiceImpl implements DateTimeService {
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");

    @Override
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now(DEFAULT_ZONE_ID);
    }
}
