package io.halljon.offerexample.offer.repository;

import io.halljon.offerexample.offer.domain.Offer;

public interface OfferRepository {
    void saveOffer(Offer offer);

    void cancelOffer(String merchantIdentifier,
                     String offerIdentifier);

    void findActiveOffersByMerchantIdentifier(String merchantIdentifier);
}
