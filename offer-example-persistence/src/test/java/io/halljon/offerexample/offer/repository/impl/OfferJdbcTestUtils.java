package io.halljon.offerexample.offer.repository.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_OFFER_ID_COLUMN;
import static java.util.Collections.singletonMap;

public class OfferJdbcTestUtils {
    public static String findOfferStatusCode(final NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                             final String offerIdentifier) {

        return namedParameterJdbcTemplate.queryForObject(
                "SELECT status_code FROM offer WHERE offer_id = :offer_id",
                singletonMap(OFFER_OFFER_ID_COLUMN, offerIdentifier), String.class);
    }

    public static Map<String, Object> findOfferValues(final NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                                      final String offerIdentifier) {

        return namedParameterJdbcTemplate.queryForMap(
                "SELECT * FROM OFFER WHERE offer_id = :offer_id",
                singletonMap(OFFER_OFFER_ID_COLUMN, offerIdentifier));
    }
}
