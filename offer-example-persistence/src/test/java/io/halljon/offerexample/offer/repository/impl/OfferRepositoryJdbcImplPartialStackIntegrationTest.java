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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static io.halljon.offerexample.offer.common.OfferConst.OFFER_STATUS_CODE_ACTIVE;
import static io.halljon.offerexample.offer.common.OfferConst.OFFER_STATUS_CODE_CANCELLED;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.createPopulatedOfferWithKnownValues;
import static io.halljon.offerexample.offer.repository.impl.OfferJdbcTestUtils.findOfferStatusCode;
import static io.halljon.offerexample.offer.repository.impl.OfferJdbcTestUtils.findOfferValues;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_ACTIVE_END_DATE_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_ACTIVE_START_DATE_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_CURRENCY_CODE_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_DESCRIPTION_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_MERCHANT_ID_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_OFFERING_ID_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_OFFER_ID_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_PRICE_COLUMN;
import static io.halljon.offerexample.offer.repository.impl.OfferRepositoryJdbcImpl.OFFER_STATUS_CODE_COLUMN;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OfferPersistenceConfiguration.class})
@EnableAutoConfiguration
@Rollback
@Transactional
public class OfferRepositoryJdbcImplPartialStackIntegrationTest {
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

        final Map<String, Object> values = findOfferValues(namedParameterJdbcTemplate, offer.getOfferIdentifier());

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

    @Sql(scripts = "classpath:sql/test-find-active-offer.sql")
    @Test
    public void findActiveOfferWhenItExists() {
        final String offerIdentifier = "repository-test-offer-id-1004";

        final Optional<Offer> optional =
                offerRepository.findActiveOffer(MERCHANT_IDENTIFIER_1, offerIdentifier, SPECIFIED_DATE_TIME);

        assertThat(optional.isPresent(), equalTo(true));

        final Offer offer = optional.get();

        assertThat(offer.getOfferIdentifier(), equalTo(offerIdentifier));
        assertThat(offer.getMerchantIdentifier(), equalTo(MERCHANT_IDENTIFIER_1));
        assertThat(offer.getDescription(), equalTo("Interesting offer 1004"));
        assertThat(offer.getOfferingIdentifier(), equalTo("offering-id-1"));
        assertThat(offer.getPrice(), equalTo(new BigDecimal("12.34")));
        assertThat(offer.getCurrencyCode(), equalTo("GBP"));
        assertThat(offer.getActiveStartDate(), equalTo(Timestamp.valueOf("2018-01-01 00:00:00")));
        assertThat(offer.getActiveEndDate(), equalTo(Timestamp.valueOf("2018-01-31 23:59:59")));
        assertThat(offer.getStatusCode(), equalTo(OFFER_STATUS_CODE_ACTIVE));
    }

    @Sql(scripts = "classpath:sql/test-find-active-offer-when-not-active.sql")
    @Test
    public void findActiveOfferWhenNotActive() {
        final Optional<Offer> optional =
                offerRepository.findActiveOffer(MERCHANT_IDENTIFIER_1, "repository-test-offer-id-1001", SPECIFIED_DATE_TIME);

        assertThat(optional.isPresent(), equalTo(false));
    }

    @Sql(scripts = "classpath:sql/test-find-active-offer-when-not-in-active-date-range.sql")
    @Test
    public void findActiveOfferWhenNotInActiveDateRange() {
        final Optional<Offer> optional =
                offerRepository.findActiveOffer(MERCHANT_IDENTIFIER_1, "repository-test-offer-id-1002", SPECIFIED_DATE_TIME);

        assertThat(optional.isPresent(), equalTo(false));
    }

    @Sql(scripts = "classpath:sql/test-cancel-offer.sql")
    @Test
    public void cancelOfferWhenItExists() {
        final String offerIdentifier = "repository-test-offer-id-1008";

        final boolean cancelled = offerRepository.cancelOffer(MERCHANT_IDENTIFIER_1, offerIdentifier);

        assertThat(cancelled, equalTo(true));

        final String statusCode = findOfferStatusCode(namedParameterJdbcTemplate, offerIdentifier);

        assertThat(statusCode, equalTo(OFFER_STATUS_CODE_CANCELLED));
    }

    @Test
    public void cancelOfferWhenItDoesNotExist() {
        final String offerIdentifier = "repository-test-offer-id-9999";

        final boolean cancelled = offerRepository.cancelOffer(MERCHANT_IDENTIFIER_1, offerIdentifier);

        assertThat(cancelled, equalTo(false));
    }

    @Sql(scripts = "classpath:sql/test-find-active-offers-when-they-exist.sql")
    @Test
    public void findActiveOffersWhenTheyExist() {
        final Collection<Offer> offers = offerRepository.findActiveOffers(MERCHANT_IDENTIFIER_1, SPECIFIED_DATE_TIME);

        assertThat(offers.size(), equalTo(3));
    }

    @Sql(scripts = "classpath:sql/test-find-active-offers-when-they-do-not-exist.sql")
    @Test
    public void findActiveOffersWhenTheyDoNotExist() {
        final Collection<Offer> offers = offerRepository.findActiveOffers(MERCHANT_IDENTIFIER_1, SPECIFIED_DATE_TIME);

        assertThat(offers.size(), equalTo(0));
    }
}
