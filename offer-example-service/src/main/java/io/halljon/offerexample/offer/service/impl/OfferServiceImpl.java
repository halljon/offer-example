package io.halljon.offerexample.offer.service.impl;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.repository.OfferRepository;
import io.halljon.offerexample.offer.service.DateService;
import io.halljon.offerexample.offer.service.IdentifierGenerator;
import io.halljon.offerexample.offer.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

@Transactional
@Service
public class OfferServiceImpl implements OfferService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);

    private final OfferRepository offerRepository;
    private final IdentifierGenerator generator;
    private final DateService dateService;

    public OfferServiceImpl(final OfferRepository offerRepository,
                            final IdentifierGenerator generator,
                            final DateService dateService) {

        this.offerRepository = offerRepository;
        this.generator = generator;
        this.dateService = dateService;
    }

    @Override
    public String createNewOffer(final String merchantIdentifier,
                                 final Offer offer) {

        final String identifier = generator.generateIdentifier();
        offer.setOfferIdentifier(identifier);
        offer.setMerchantIdentifier(merchantIdentifier);

        LOGGER.trace("About to create new offer for merchant: '{}', with offer identifier: '{}'", merchantIdentifier, identifier);

        offerRepository.saveNewOffer(offer);

        return identifier;
    }

    @Override
    public void cancelOffer(final String merchantIdentifier,
                            final String offerIdentifier) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Offer> findActiveOffers(final String merchantIdentifier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Offer> findActiveOffer(final String merchantIdentifier,
                                           final String offerIdentifier) {

        final Timestamp dateTime = Timestamp.valueOf(dateService.getCurrentDateTime());

        return offerRepository.findActiveOffer(merchantIdentifier, offerIdentifier, dateTime);
    }
}
