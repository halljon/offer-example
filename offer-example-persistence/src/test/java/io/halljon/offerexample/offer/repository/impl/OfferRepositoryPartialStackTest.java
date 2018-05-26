package io.halljon.offerexample.offer.repository.impl;

import io.halljon.offerexample.offer.config.OfferPersistenceConfiguration;
import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.repository.OfferRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OfferPersistenceConfiguration.class})
@EnableAutoConfiguration
@Rollback
@Transactional
public class OfferRepositoryPartialStackTest {
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");
    private static final LocalDateTime NOW = LocalDateTime.now(DEFAULT_ZONE_ID);
    private static final String OFFER_IDENTIFIER = "offer-123";
    private static final String MERCHANT_IDENTIFIER = "merchant-123";
    private static final String DESCRIPTION = "DESCRIPTION-123";
    private static final String OFFERING_IDENTIFIER = "offering-123";
    private static final BigDecimal PRICE = new BigDecimal("1.23");
    private static final String CURRENCY_CODE = "USD";
    private static final Timestamp ACTIVE_START_DATE = Timestamp.valueOf(NOW.minusDays(1));
    private static final Timestamp ACTIVE_END_DATE = Timestamp.valueOf(NOW);
    private static final String STATUS_CODE = "C";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private OfferRepository offerRepository;

    @Test
    public void saveNewOffer() {
        offerRepository.saveOffer(createPopulatedOffer());

        final Map<String, Object> values = namedParameterJdbcTemplate.queryForMap(
                "SELECT * FROM OFFER WHERE offer_id = :offer_id", singletonMap("offer_id", OFFER_IDENTIFIER));

        assertThat(values.get("offer_id"), equalTo(OFFER_IDENTIFIER));
        assertThat(values.get("merchant_id"), equalTo(MERCHANT_IDENTIFIER));
        assertThat(values.get("description"), equalTo(DESCRIPTION));
        assertThat(values.get("offering_id"), equalTo(OFFERING_IDENTIFIER));
        assertThat(values.get("price"), equalTo(PRICE));
        assertThat(values.get("currency_code"), equalTo(CURRENCY_CODE));
        assertThat(values.get("active_start_date"), equalTo(ACTIVE_START_DATE));
        assertThat(values.get("active_end_date"), equalTo(ACTIVE_END_DATE));
        assertThat(values.get("status_code"), equalTo(STATUS_CODE));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveNewOfferWithInvalidCurrencyCode() {
        final Offer offer = createPopulatedOffer();
        offer.setCurrencyCode("Z!Z");

        offerRepository.saveOffer(offer);
    }

    private Offer createPopulatedOffer() {
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
