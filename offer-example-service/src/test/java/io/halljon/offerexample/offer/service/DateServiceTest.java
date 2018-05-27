package io.halljon.offerexample.offer.service;

import io.halljon.offerexample.offer.service.impl.DateServiceImpl;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class DateServiceTest {
    private final DateService dateService = new DateServiceImpl();

    @Test
    public void getCurrentDateTime() {
        final LocalDateTime dateTime = dateService.getCurrentDateTime();

        assertThat(dateTime, notNullValue());
    }
}
