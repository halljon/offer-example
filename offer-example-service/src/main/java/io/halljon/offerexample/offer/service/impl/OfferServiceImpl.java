package io.halljon.offerexample.offer.service.impl;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.repository.OfferRepository;
import io.halljon.offerexample.offer.service.DateTimeService;
import io.halljon.offerexample.offer.service.IdentifierGenerator;
import io.halljon.offerexample.offer.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Transactional
@Service
public class OfferServiceImpl implements OfferService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);

    private final OfferRepository offerRepository;
    private final IdentifierGenerator generator;
    private final DateTimeService dateTimeService;

    public OfferServiceImpl(final OfferRepository offerRepository,
                            final IdentifierGenerator generator,
                            final DateTimeService dateTimeService) {

        this.offerRepository = offerRepository;
        this.generator = generator;
        this.dateTimeService = dateTimeService;
    }

    @Override
    public String createNewOffer(final String merchantIdentifier,
                                 final Offer offer) {

        final String identifier = generator.generateIdentifier();
        offer.setOfferIdentifier(identifier);
        offer.setMerchantIdentifier(merchantIdentifier);

        LOGGER.debug("About to create new offer for merchant: '{}', with offer identifier: '{}'",
                merchantIdentifier, identifier);

        offerRepository.saveNewOffer(offer);

        return identifier;
    }

    @Override
    public boolean cancelOffer(final String merchantIdentifier,
                               final String offerIdentifier) {

        LOGGER.debug("About to cancel offer for merchant: '{}', with offer identifier: '{}'",
                merchantIdentifier, offerIdentifier);

        return offerRepository.cancelOffer(merchantIdentifier, offerIdentifier);
    }

    @Override
    public Collection<Offer> findActiveOffers(final String merchantIdentifier) {
        final LocalDateTime dateTime = dateTimeService.getCurrentDateTime();
        final Timestamp timestamp = Timestamp.valueOf(dateTime);

        LOGGER.debug("About to find active offers for merchant: '{}' using current time: '{}'",
                merchantIdentifier, dateTime);

        return offerRepository.findActiveOffers(merchantIdentifier, timestamp);
    }

    @Override
    public Optional<Offer> findActiveOffer(final String merchantIdentifier,
                                           final String offerIdentifier) {

        final LocalDateTime dateTime = dateTimeService.getCurrentDateTime();
        final Timestamp timestamp = Timestamp.valueOf(dateTime);

        LOGGER.debug("About to find active offer for merchant: '{}', with offer identifier: '{}' using current time: '{}'",
                merchantIdentifier, offerIdentifier, dateTime);

        return offerRepository.findActiveOffer(merchantIdentifier, offerIdentifier, timestamp);
    }
}
