package io.halljon.offerexample.offer.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class OfferTestUtils {
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");
    public static final LocalDateTime NOW = LocalDateTime.now(DEFAULT_ZONE_ID);
    public static final String OFFER_IDENTIFIER = "offer-123";
    public static final String MERCHANT_IDENTIFIER = "merchant-123";
    public static final String DESCRIPTION = "DESCRIPTION-123";
    public static final String OFFERING_IDENTIFIER = "offering-123";
    public static final BigDecimal PRICE = new BigDecimal("1.23");
    public static final String CURRENCY_CODE = "USD";
    public static final Timestamp ACTIVE_START_DATE = Timestamp.valueOf(NOW.minusDays(12));
    public static final Timestamp ACTIVE_END_DATE = Timestamp.valueOf(NOW.plusDays(23));
    public static final String STATUS_CODE = "C";

    public OfferTestUtils() {
        // publicconstructor to prevent creation
    }

    public static Offer createPopulatedOffer() {
        final Offer offer = new Offer();
        offer.setOfferIdentifier(OFFER_IDENTIFIER);
        offer.setMerchantIdentifier(MERCHANT_IDENTIFIER);
        offer.setDescription(DESCRIPTION);
        offer.setOfferingIdentifier(OFFERING_IDENTIFIER);
        offer.setPrice(PRICE);
        offer.setCurrencyCode(CURRENCY_CODE);
        offer.setActiveStartDate(ACTIVE_START_DATE);
        offer.setActiveEndDate(ACTIVE_END_DATE);
        offer.setStatusCode(STATUS_CODE);

        return offer;
    }
}
