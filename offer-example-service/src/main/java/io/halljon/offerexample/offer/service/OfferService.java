package io.halljon.offerexample.offer.service;

import io.halljon.offerexample.offer.domain.Offer;

import java.util.Collection;
import java.util.Optional;

public interface OfferService {
    String createNewOffer(String merchantIdentifier,
                          Offer offer);

    void cancelOffer(String merchantIdentifier,
                     String offerIdentifier);

    Collection<Offer> findActiveOffers(String merchantIdentifier);

    Optional<Offer> findActiveOffer(String merchantIdentifier,
                                    String offerIdentifier);
}
