package io.halljon.offerexample.offer.controller;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.service.OfferService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/v1/offers")
public class CustomerOfferController {
    private final OfferService offerService;

    public CustomerOfferController(final OfferService offerService) {
        this.offerService = offerService;
    }

    public Collection<Offer> findAllActiveOffers(final String merchantIdentifier) {
        return offerService.findActiveOffers(merchantIdentifier);
    }

    public Offer findActiveOffer(final String merchantIdentifier,
                                 final String offerIdentifier) {

        return offerService.findActiveOffer(merchantIdentifier, offerIdentifier);
    }
}
