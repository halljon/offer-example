package io.halljon.offerexample.offer.service;

import io.halljon.offerexample.offer.domain.Offer;

import java.util.Collection;

public interface OfferService {
    String saveOffer(String merchantIdentifier,
                     Offer offer);

    void cancelOffer(String merchantIdentifier,
                     String offerIdentifier);

    Collection<Offer> findActiveOffers(String merchantIdentifier);

    Collection<Offer> findAllOffers(String merchantIdentifier);

    Offer findActiveOffer(String merchantIdentifier,
                          String offerIdentifier);
}
