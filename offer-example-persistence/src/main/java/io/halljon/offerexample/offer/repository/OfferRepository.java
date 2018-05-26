package io.halljon.offerexample.offer.repository;

import io.halljon.offerexample.offer.domain.Offer;

import java.time.LocalDateTime;
import java.util.Collection;

public interface OfferRepository {
    void saveOffer(Offer offer);

    void cancelOffer(String merchantIdentifier,
                     String offerIdentifier);

    Collection<Offer> findActiveOffers(String merchantIdentifier,
                                       LocalDateTime dateTime);

    Collection<Offer> findAllOffers(String merchantIdentifier);

    Offer findActiveOffer(String merchantIdentifier,
                          String offerIdentifier);
}
