package io.halljon.offerexample.offer.controller;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.service.OfferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        if (true) {
            throw new UnsupportedOperationException();
        }

        return offerService.findActiveOffers(merchantIdentifier);
    }

    @GetMapping(value = "/{merchantIdentifier}/{offerIdentifier}")
    public Offer findActiveOffer(@PathVariable("merchantIdentifier") final String merchantIdentifier,
                                 @PathVariable("offerIdentifier") final String offerIdentifier) {

        return offerService.findActiveOffer(merchantIdentifier, offerIdentifier);
    }
}
