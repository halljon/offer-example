package io.halljon.offerexample.offer.controller;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.service.OfferService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/offers")
public class MerchantOfferController {
    private final OfferService offerService;

    public MerchantOfferController(final OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("merchandIdentifier")
    public String postOffer(@PathVariable("merchantIdentifier") final String merchantIdentifier,
                            final Offer offer) {

        return offerService.saveOffer(merchantIdentifier, offer);
    }

    @DeleteMapping({"merchantIdentifier/offerIdentifier"})
    public void deleteOffer(@PathVariable("merchantIdentifier") final String merchantIdentifier,
                            @PathVariable("offerIdentifier") final String offerIdentifier) {

        /*
            If found and then deleted, then return good code
            If not found, then return 404?
         */
        offerService.cancelOffer(merchantIdentifier, offerIdentifier);
    }
}
