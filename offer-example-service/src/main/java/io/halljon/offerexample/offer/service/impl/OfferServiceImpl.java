package io.halljon.offerexample.offer.service.impl;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.repository.OfferRepository;
import io.halljon.offerexample.offer.service.IdentifierGenerator;
import io.halljon.offerexample.offer.service.OfferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final IdentifierGenerator generator;

    public OfferServiceImpl(final OfferRepository offerRepository,
                            final IdentifierGenerator generator) {

        this.offerRepository = offerRepository;
        this.generator = generator;
    }

    @Override
    public String createNewOffer(final String merchantIdentifier,
                                 final Offer offer) {

        final String identifier = generator.generateIdentifier();
        offer.setOfferIdentifier(identifier);
        offer.setMerchantIdentifier(merchantIdentifier);

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
    public Collection<Offer> findAllOffers(final String merchantIdentifier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Offer findActiveOffer(final String merchantIdentifier,
                                 final String offerIdentifier) {

        throw new UnsupportedOperationException();
    }
}
