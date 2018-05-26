package io.halljon.offerexample.offer.controller;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.service.OfferService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/offers")
public class MerchantOfferController {
    private final OfferService offerService;

    public MerchantOfferController(final OfferService offerService) {
        this.offerService = offerService;
    }

    public String saveOffer(final Offer offer) {
        return offerService.saveOffer(offer);
    }

    public void cancelOffer(final String merchantIdentifier,
                            final String offerIdentifier) {

        offerService.cancelOffer(merchantIdentifier, offerIdentifier);
    }
}
