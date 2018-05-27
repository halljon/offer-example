package io.halljon.offerexample.offer.service.impl;

import io.halljon.offerexample.offer.service.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdentifierGeneratorImpl implements IdentifierGenerator {
    @Override
    public String generateIdentifier() {
        return UUID.randomUUID().toString();
    }
}
