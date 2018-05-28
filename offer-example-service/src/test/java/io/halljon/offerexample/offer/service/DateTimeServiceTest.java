package io.halljon.offerexample.offer.service;

import io.halljon.offerexample.offer.service.impl.DateTimeServiceImpl;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class DateTimeServiceTest {
    private final DateTimeService dateTimeService = new DateTimeServiceImpl();

    @Test
    public void getCurrentDateTime() {
        final LocalDateTime dateTime = dateTimeService.getCurrentDateTime();

        assertThat(dateTime, notNullValue());
    }
}
