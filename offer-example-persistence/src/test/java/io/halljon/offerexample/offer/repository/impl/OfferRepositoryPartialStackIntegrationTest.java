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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import static io.halljon.offerexample.offer.domain.OfferTestUtils.createPopulatedOfferWithKnownValues;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_ACTIVE_END_DATE_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_ACTIVE_START_DATE_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_CURRENCY_CODE_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_DESCRIPTION_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_MERCHANT_ID_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_OFFERING_ID_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_OFFER_ID_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_PRICE_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_STATUS_CODE_COLUMN;
import static java.util.Collections.singletonMap;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OfferPersistenceConfiguration.class})
@EnableAutoConfiguration
@Rollback
@Transactional
@Sql(scripts = "classpath:sql/repository-test-preparation.sql", executionPhase = BEFORE_TEST_METHOD)
public class OfferRepositoryPartialStackIntegrationTest {
    private static final String MERCHANT_IDENTIFIER_1 = "repository-test-merchant-id-1";
    private static final Timestamp SPECIFIED_DATE_TIME = Timestamp.valueOf("2018-01-15 12:13:14");

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private OfferRepository offerRepository;

    @Test
    public void saveNewOffer() {
        final Offer offer = createPopulatedOfferWithKnownValues();

        offerRepository.saveNewOffer(offer);

        final Map<String, Object> values = namedParameterJdbcTemplate.queryForMap(
                "SELECT * FROM OFFER WHERE offer_id = :offer_id",
                singletonMap(OFFER_OFFER_ID_COLUMN, offer.getOfferIdentifier()));

        assertThat(values.get(OFFER_OFFER_ID_COLUMN), equalTo(offer.getOfferIdentifier()));
        assertThat(values.get(OFFER_MERCHANT_ID_COLUMN), equalTo(offer.getMerchantIdentifier()));
        assertThat(values.get(OFFER_DESCRIPTION_COLUMN), equalTo(offer.getDescription()));
        assertThat(values.get(OFFER_OFFERING_ID_COLUMN), equalTo(offer.getOfferingIdentifier()));
        assertThat(values.get(OFFER_PRICE_COLUMN), equalTo(offer.getPrice()));
        assertThat(values.get(OFFER_CURRENCY_CODE_COLUMN), equalTo(offer.getCurrencyCode()));
        assertThat(values.get(OFFER_ACTIVE_START_DATE_COLUMN), equalTo(offer.getActiveStartDate()));
        assertThat(values.get(OFFER_ACTIVE_END_DATE_COLUMN), equalTo(offer.getActiveEndDate()));
        assertThat(values.get(OFFER_STATUS_CODE_COLUMN), equalTo(offer.getStatusCode()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveNewOfferWhenCurrencyCodeInvalid() {
        final Offer offer = createPopulatedOfferWithKnownValues();
        offer.setCurrencyCode("Z!Z");

        offerRepository.saveNewOffer(offer);
    }

    @Test
    public void findActiveOfferWhenExists() {
        final String offerIdentifier = "offer-id-1004";

        final Offer offer = offerRepository.findActiveOffer(MERCHANT_IDENTIFIER_1, offerIdentifier, SPECIFIED_DATE_TIME);

        assertThat(offer.getOfferIdentifier(), equalTo(offerIdentifier));
        assertThat(offer.getMerchantIdentifier(), equalTo(MERCHANT_IDENTIFIER_1));
        assertThat(offer.getDescription(), equalTo("Some really interesting offer 1004"));
        assertThat(offer.getOfferingIdentifier(), equalTo("offering-id-1"));
        assertThat(offer.getPrice(), equalTo(new BigDecimal("12.34")));
        assertThat(offer.getCurrencyCode(), equalTo("GBP"));
        assertThat(offer.getActiveStartDate(), equalTo(Timestamp.valueOf("2018-01-01 00:00:00")));
        assertThat(offer.getActiveEndDate(), equalTo(Timestamp.valueOf("2018-01-31 23:59:59")));
        assertThat(offer.getStatusCode(), equalTo("A"));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findActiveOfferWhenNotActive() {
        offerRepository.findActiveOffer(MERCHANT_IDENTIFIER_1, "offer-id-1001", SPECIFIED_DATE_TIME);

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findActiveOfferWhenNotInActiveDateRange() {
        offerRepository.findActiveOffer(MERCHANT_IDENTIFIER_1, "offer-id-1002", SPECIFIED_DATE_TIME);
    }
}
