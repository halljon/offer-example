package io.halljon.offerexample.identifier.impl;

import io.halljon.offerexample.identifier.IdentifierGenerator;
import org.junit.Test;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class IdentifierGeneratorImplTest {
    private IdentifierGenerator generator = new IdentifierGeneratorImpl();

    @Test
    public void generateIdentifier() {
        final String identifier = generator.generateIdentifier();

        assertThat(identifier, notNullValue());
    }
}