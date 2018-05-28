package io.halljon.offerexample.offer.service;

import io.halljon.offerexample.offer.domain.Offer;

import java.util.Collection;
import java.util.Optional;

public interface OfferService {
    /**
     * Creates a new offer.
     *
     * @param merchantIdentifier Merchant identifier.
     * @param offer              Offer to create.
     * @return assigned offer identifier.
     */
    String createNewOffer(String merchantIdentifier,
                          Offer offer);

    /**
     * Cancels an offer.
     *
     * @param merchantIdentifier Merchant identifier.
     * @param offerIdentifier    Offer identifier.
     * @return <b>true</b> if offer was cancelled (even if it has been previously cancelled),
     * <b>false</b> if offer didn't exist.
     */
    boolean cancelOffer(String merchantIdentifier,
                        String offerIdentifier);

    /**
     * Find active offers.
     *
     * @param merchantIdentifier Merchant identifier.
     * @return collection of active offers.
     */
    Collection<Offer> findActiveOffers(String merchantIdentifier);

    /**
     * Finds a specific active offer.
     *
     * @param merchantIdentifier Merchant identifier.
     * @param offerIdentifier    Offer identifier.
     * @return optional containing offer if found.
     */
    Optional<Offer> findActiveOffer(String merchantIdentifier,
                                    String offerIdentifier);
}
