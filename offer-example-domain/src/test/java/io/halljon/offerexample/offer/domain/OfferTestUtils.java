package io.halljon.offerexample.offer.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static io.halljon.offerexample.offer.common.OfferConst.DEFAULT_ZONE_ID;
import static io.halljon.offerexample.offer.common.OfferConst.OFFER_STATUS_CODE_ACTIVE;

public final class OfferTestUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final LocalDateTime LOCAL_DATE_TIME_NOW = LocalDateTime.now(DEFAULT_ZONE_ID);
    private static final String KNOWN_OFFER_IDENTIFIER_1 = "offer-123";
    private static final String KNOWN_MERCHANT_IDENTIFIER_1 = "merchant-123";
    private static final String KNOWN_DESCRIPTION_1 = "DESCRIPTION-123";
    private static final String KNOWN_OFFERING_IDENTIFIER_1 = "offering-123";
    private static final BigDecimal KNOWN_PRICE_1 = new BigDecimal("1.23");
    private static final String KNOWN_CURRENCY_CODE_1 = "USD";
    private static final Timestamp KNOWN_ACTIVE_START_DATE_1 = Timestamp.valueOf(LOCAL_DATE_TIME_NOW.minusDays(12));
    private static final Timestamp KNOWN_ACTIVE_END_DATE_1 = Timestamp.valueOf(LOCAL_DATE_TIME_NOW.plusDays(23));
    private static final String KNOWN_STATUS_CODE_1 = OFFER_STATUS_CODE_ACTIVE;

    private OfferTestUtils() {
        // Private constructor to prevent creation
    }

    public static Offer createPopulatedOfferWithKnownValues() {
        final Offer offer = new Offer();
        offer.setOfferIdentifier(KNOWN_OFFER_IDENTIFIER_1);
        offer.setMerchantIdentifier(KNOWN_MERCHANT_IDENTIFIER_1);
        offer.setDescription(KNOWN_DESCRIPTION_1);
        offer.setOfferingIdentifier(KNOWN_OFFERING_IDENTIFIER_1);
        offer.setPrice(KNOWN_PRICE_1);
        offer.setCurrencyCode(KNOWN_CURRENCY_CODE_1);
        offer.setActiveStartDate(KNOWN_ACTIVE_START_DATE_1);
        offer.setActiveEndDate(KNOWN_ACTIVE_END_DATE_1);
        offer.setStatusCode(KNOWN_STATUS_CODE_1);

        return offer;
    }

    public static String toJson(final Object object)
            throws JsonProcessingException {

        return OBJECT_MAPPER.writeValueAsString(object);
    }
}
