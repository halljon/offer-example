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
        final Offer offer = createPopulatedOfferWithKnownValues();

        offerRepository.saveOffer(offer);

        final Map<String, Object> values = namedParameterJdbcTemplate.queryForMap(
                "SELECT * FROM OFFER WHERE offer_id = :offer_id", singletonMap("offer_id", offer.getOfferIdentifier()));

        assertThat(values.get("offer_id"), equalTo(offer.getOfferIdentifier()));
        assertThat(values.get("merchant_id"), equalTo(offer.getMerchantIdentifier()));
        assertThat(values.get("description"), equalTo(offer.getDescription()));
        assertThat(values.get("offering_id"), equalTo(offer.getOfferingIdentifier()));
        assertThat(values.get("price"), equalTo(offer.getPrice()));
        assertThat(values.get("currency_code"), equalTo(offer.getCurrencyCode()));
        assertThat(values.get("active_start_date"), equalTo(offer.getActiveStartDate()));
        assertThat(values.get("active_end_date"), equalTo(offer.getActiveEndDate()));
        assertThat(values.get("status_code"), equalTo(offer.getStatusCode()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveNewOfferWithInvalidCurrencyCode() {
        final Offer offer = createPopulatedOfferWithKnownValues();
        offer.setCurrencyCode("Z!Z");

        offerRepository.saveOffer(offer);
    }
}
