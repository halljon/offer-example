package io.halljon.offerexample.offer.service;

import io.halljon.offerexample.offer.service.impl.IdentifierGeneratorImpl;
import org.junit.Test;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class IdentifierGeneratorTest {
    private IdentifierGenerator generator = new IdentifierGeneratorImpl();

    @Test
    public void generateIdentifier() {
        final String identifier = generator.generateIdentifier();

        assertThat(identifier, notNullValue());
    }
}
