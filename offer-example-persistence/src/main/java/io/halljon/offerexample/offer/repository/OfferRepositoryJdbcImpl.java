package io.halljon.offerexample.offer.repository;

import io.halljon.offerexample.offer.domain.Offer;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public class OfferRepositoryJdbcImpl implements OfferRepository{
    @Override
    public void saveOffer(Offer offer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void cancelOffer(String merchantIdentifier, String offerIdentifier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Offer> findActiveOffers(String merchantIdentifier, LocalDateTime dateTime) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Offer> findAllOffers(String merchantIdentifier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Offer findActiveOffer(String merchantIdentifier, String offerIdentifier) {
        throw new UnsupportedOperationException();
    }
}
