package io.halljon.offerexample.offer.controller;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

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
    public ResponseEntity<Offer> findActiveOffer(@PathVariable("merchantIdentifier") final String merchantIdentifier,
                                                 @PathVariable("offerIdentifier") final String offerIdentifier) {

        final Optional<Offer> optional = offerService.findActiveOffer(merchantIdentifier, offerIdentifier);

        return optional.map(offer -> new ResponseEntity<>(offer, OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }
}
