package io.halljon.offerexample.offer.common;

import java.time.ZoneId;

public final class OfferConst {
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");

    //TODO: JH - change status codes to enum and then do mapping in repository
    public static final String OFFER_STATUS_CODE_ACTIVE = "A";
    public static final String OFFER_STATUS_CODE_CANCELLED = "C";

    private OfferConst() {
        // Private constructor to prevent creation
    }
}
