package io.halljon.offerexample.offer.repository;

import io.halljon.offerexample.offer.domain.Offer;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

public interface OfferRepository {
    void saveNewOffer(Offer offer);

    boolean cancelOffer(String merchantIdentifier,
                        String offerIdentifier);

    Collection<Offer> findActiveOffers(String merchantIdentifier,
                                       Timestamp dateTime);

    Optional<Offer> findActiveOffer(String merchantIdentifier,
                                    String offerIdentifier,
                                    Timestamp dateTime);
}
