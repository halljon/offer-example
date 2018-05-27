package io.halljon.offerexample.offer.common;

import java.time.ZoneId;

public final class OfferConst {
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");

    private OfferConst() {
        // Private constructor to prevent creation
    }
}
