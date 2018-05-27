package io.halljon.offerexample.offer.controller;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.service.OfferService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/offers")
public class MerchantOfferController {
    private final OfferService offerService;

    public MerchantOfferController(final OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping(value = "/{merchantIdentifier}")
    public String saveNewOffer(@PathVariable("merchantIdentifier") final String merchantIdentifier,
                               @RequestBody final Offer offer) {

        return offerService.createNewOffer(merchantIdentifier, offer);
    }

    @DeleteMapping(value = "/{merchantIdentifier}/{offerIdentifier}")
    public void cancelOffer(@PathVariable("merchantIdentifier") final String merchantIdentifier,
                            @PathVariable("offerIdentifier") final String offerIdentifier) {

        if (true) {
            throw new UnsupportedOperationException();
        }

        /*
            If found and then deleted, then return good code
            If not found, then return 404?
         */
        offerService.cancelOffer(merchantIdentifier, offerIdentifier);
    }
}
