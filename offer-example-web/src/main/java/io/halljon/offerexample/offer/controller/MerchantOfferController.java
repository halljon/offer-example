package io.halljon.offerexample.offer.controller;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/offers")
public class MerchantOfferController {
    private final OfferService offerService;

    public MerchantOfferController(final OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping(value = "/{merchantIdentifier}")
    public ResponseEntity<String> createNewOffer(@PathVariable("merchantIdentifier") final String merchantIdentifier,
                                                 @Valid @RequestBody final Offer offer) {

        final String offerIdentifier = offerService.createNewOffer(merchantIdentifier, offer);

        return new ResponseEntity<>(offerIdentifier, CREATED);
    }

    @DeleteMapping(value = "/{merchantIdentifier}/{offerIdentifier}")
    public ResponseEntity<Void> cancelOffer(@PathVariable("merchantIdentifier") final String merchantIdentifier,
                                            @PathVariable("offerIdentifier") final String offerIdentifier) {


        boolean cancelled = offerService.cancelOffer(merchantIdentifier, offerIdentifier);

        return cancelled ? new ResponseEntity<>(OK) : new ResponseEntity<>(NOT_FOUND);
    }
}
