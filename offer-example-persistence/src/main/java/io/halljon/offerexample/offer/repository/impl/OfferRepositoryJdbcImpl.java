package io.halljon.offerexample.offer.repository.impl;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.repository.OfferRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class OfferRepositoryJdbcImpl implements OfferRepository {
    private static final String SAVE_OFFER_SQL = "INSERT INTO offer ("
            + "    offer_id, merchant_id, description,"
            + "    offering_id, price, currency_code,"
            + "    active_start_date, active_end_date,"
            + "    status_code"
            + ") VALUES ("
            + "    :offer_id, :merchant_id, :description,"
            + "    :offering_id, :price, :currency_code,"
            + "    :active_start_date, :active_end_date,"
            + "    :status_code"
            + ");";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OfferRepositoryJdbcImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveOffer(final Offer offer) {
        final Map<String, Object> values = new HashMap<>();
        values.put("offer_id", offer.getOfferIdentifier());
        values.put("merchant_id", offer.getMerchantIdentifier());
        values.put("description", offer.getDescription());
        values.put("offering_id", offer.getOfferingIdentifier());
        values.put("price", offer.getPrice());
        values.put("currency_code", offer.getCurrencyCode());
        values.put("active_start_date", offer.getActiveStartDate());
        values.put("active_end_date", offer.getActiveEndDate());
        values.put("status_code", offer.getStatusCode());

        namedParameterJdbcTemplate.update(SAVE_OFFER_SQL, values);
    }

    @Override
    public void cancelOffer(final String merchantIdentifier,
                            final String offerIdentifier) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Offer> findActiveOffers(final String merchantIdentifier,
                                              final LocalDateTime dateTime) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Offer> findAllOffers(final String merchantIdentifier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Offer findActiveOffer(final String merchantIdentifier,
                                 final String offerIdentifier) {

        throw new UnsupportedOperationException();
    }
}
