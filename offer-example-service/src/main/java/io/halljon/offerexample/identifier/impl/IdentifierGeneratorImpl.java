package io.halljon.offerexample.identifier.impl;

import io.halljon.offerexample.identifier.IdentifierGenerator;

import java.util.UUID;

public class IdentifierGeneratorImpl implements IdentifierGenerator {
    @Override
    public String generateIdentifier() {
        return UUID.randomUUID().toString();
    }
}
