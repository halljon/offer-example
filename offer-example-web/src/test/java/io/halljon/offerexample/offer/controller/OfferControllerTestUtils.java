package io.halljon.offerexample.offer.controller;

final class OfferControllerTestUtils {
    private OfferControllerTestUtils() {
        // Private constructor to prevent creation.
    }

    static String createOfferUrlTemplateWithMerchantIdentifier() {
        return "/v1/offers/{merchantIdentifier}";
    }

    static String createOfferUrlTemplateWithMerchantAndOfferIdentifiers() {
        return "/v1/offers/{merchantIdentifier}/{offerIdentifier}";
    }
}
