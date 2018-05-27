package io.halljon.offerexample.offer.repository.impl;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.repository.OfferRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class OfferRepositoryJdbcImpl implements OfferRepository {
    public static final String OFFER_OFFER_ID_COLUMN = "offer_id";
    public static final String OFFER_MERCHANT_ID_COLUMN = "merchant_id";
    public static final String OFFER_DESCRIPTION_COLUMN = "description";
    public static final String OFFER_OFFERING_ID_COLUMN = "offering_id";
    public static final String OFFER_PRICE_COLUMN = "price";
    public static final String OFFER_CURRENCY_CODE_COLUMN = "currency_code";
    public static final String OFFER_ACTIVE_START_DATE_COLUMN = "active_start_date";
    public static final String OFFER_ACTIVE_END_DATE_COLUMN = "active_end_date";
    public static final String OFFER_STATUS_CODE_COLUMN = "status_code";

    //TODO: JH - externalise SQL
    private static final String SAVE_NEW_OFFER_SQL =
            "INSERT INTO offer ("
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

    private static final String FIND_ACTIVE_OFFER_SQL =
            "SELECT *"
                    + "FROM offer"
                    + "WHERE :specified_date >= active_start_date"
                    + "AND :specified_date <= active_end_date"
                    + "AND status_code = 'A'";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OfferRepositoryJdbcImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveNewOffer(final Offer offer) {
        final Map<String, Object> values = new HashMap<>();
        values.put(OFFER_OFFER_ID_COLUMN, offer.getOfferIdentifier());
        values.put(OFFER_MERCHANT_ID_COLUMN, offer.getMerchantIdentifier());
        values.put(OFFER_DESCRIPTION_COLUMN, offer.getDescription());
        values.put(OFFER_OFFERING_ID_COLUMN, offer.getOfferingIdentifier());
        values.put(OFFER_PRICE_COLUMN, offer.getPrice());
        values.put(OFFER_CURRENCY_CODE_COLUMN, offer.getCurrencyCode());
        values.put(OFFER_ACTIVE_START_DATE_COLUMN, offer.getActiveStartDate());
        values.put(OFFER_ACTIVE_END_DATE_COLUMN, offer.getActiveEndDate());
        values.put(OFFER_STATUS_CODE_COLUMN, offer.getStatusCode());

        namedParameterJdbcTemplate.update(SAVE_NEW_OFFER_SQL, values);
    }

    @Override
    public void cancelOffer(final String merchantIdentifier,
                            final String offerIdentifier) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Offer> findActiveOffers(final String merchantIdentifier,
                                              final Timestamp dateTime) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Offer> findAllOffers(final String merchantIdentifier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Offer findActiveOffer(final String merchantIdentifier,
                                 final String offerIdentifier,
                                 final Timestamp dateTime) {

        throw new UnsupportedOperationException();
    }
}
