package io.halljon.offerexample.offer.repository;

import io.halljon.offerexample.offer.domain.Offer;

import java.sql.Timestamp;
import java.util.Collection;

public interface OfferRepository {
    void saveNewOffer(Offer offer);

    void cancelOffer(String merchantIdentifier,
                     String offerIdentifier);

    Collection<Offer> findActiveOffers(String merchantIdentifier,
                                       Timestamp dateTime);

    Collection<Offer> findAllOffers(String merchantIdentifier);

    Offer findActiveOffer(String merchantIdentifier,
                          String offerIdentifier,
                          Timestamp dateTime);
}
