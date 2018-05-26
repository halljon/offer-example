package io.halljon.offerexample.identifier.impl;

import io.halljon.offerexample.identifier.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdentifierGeneratorImpl implements IdentifierGenerator {
    @Override
    public String generateIdentifier() {
        return UUID.randomUUID().toString();
    }
}
