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
import java.util.Map;

import static io.halljon.offerexample.offer.domain.OfferTestUtils.KNOWN_ACTIVE_END_DATE_1;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.KNOWN_ACTIVE_START_DATE_1;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.KNOWN_CURRENCY_CODE_1;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.KNOWN_DESCRIPTION_1;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.KNOWN_MERCHANT_IDENTIFIER_1;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.KNOWN_OFFERING_IDENTIFIER_1;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.KNOWN_OFFER_IDENTIFIER_1;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.KNOWN_PRICE_1;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.KNOWN_STATUS_CODE_1;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.createPopulatedOfferWithKnownValues;
import static java.util.Collections.singletonMap;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OfferPersistenceConfiguration.class})
@EnableAutoConfiguration
@Rollback
@Transactional
public class OfferRepositoryPartialStackIntegrationTest {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private OfferRepository offerRepository;

    @Test
    public void saveNewOffer() {
        offerRepository.saveOffer(createPopulatedOfferWithKnownValues());

        final Map<String, Object> values = namedParameterJdbcTemplate.queryForMap(
                "SELECT * FROM OFFER WHERE offer_id = :offer_id", singletonMap("offer_id", KNOWN_OFFER_IDENTIFIER_1));

        assertThat(values.get("offer_id"), equalTo(KNOWN_OFFER_IDENTIFIER_1));
        assertThat(values.get("merchant_id"), equalTo(KNOWN_MERCHANT_IDENTIFIER_1));
        assertThat(values.get("description"), equalTo(KNOWN_DESCRIPTION_1));
        assertThat(values.get("offering_id"), equalTo(KNOWN_OFFERING_IDENTIFIER_1));
        assertThat(values.get("price"), equalTo(KNOWN_PRICE_1));
        assertThat(values.get("currency_code"), equalTo(KNOWN_CURRENCY_CODE_1));
        assertThat(values.get("active_start_date"), equalTo(KNOWN_ACTIVE_START_DATE_1));
        assertThat(values.get("active_end_date"), equalTo(KNOWN_ACTIVE_END_DATE_1));
        assertThat(values.get("status_code"), equalTo(KNOWN_STATUS_CODE_1));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveNewOfferWithInvalidCurrencyCode() {
        final Offer offer = createPopulatedOfferWithKnownValues();
        offer.setCurrencyCode("Z!Z");

        offerRepository.saveOffer(offer);
    }
}
