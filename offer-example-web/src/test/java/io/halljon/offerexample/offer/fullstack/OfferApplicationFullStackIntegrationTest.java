package io.halljon.offerexample.offer.fullstack;

import io.halljon.offerexample.offer.OfferApplication;
import io.halljon.offerexample.offer.domain.Offer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

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
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApplication.class, webEnvironment = RANDOM_PORT)
@Sql(scripts = "classpath:sql/full-stack-test-preparation.sql", executionPhase = BEFORE_TEST_METHOD)
public class OfferApplicationFullStackIntegrationTest {
    private static final String MERCHANT_IDENTIFIER = "full-stack-test-merchant-id";
    private static final String OFFER_IDENTIFIER = "full-stack-test-offer-id-1001";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void createNewOffer() {
        final Offer offer = createPopulatedOfferWithKnownValues();
        final HttpEntity<Offer> request = new HttpEntity<>(offer);

        final ResponseEntity<String> response = restTemplate.exchange(
                createOfferUrlWithMerchantIdentifier(), POST, request, String.class,
                MERCHANT_IDENTIFIER);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), notNullValue());

        final String identifierOfCreatedOffer = response.getBody();

        final Map<String, Object> values = findOfferValues(namedParameterJdbcTemplate, identifierOfCreatedOffer);

        assertThat(values.get(OFFER_OFFER_ID_COLUMN), equalTo(identifierOfCreatedOffer));
        assertThat(values.get(OFFER_MERCHANT_ID_COLUMN), equalTo(MERCHANT_IDENTIFIER));
        assertThat(values.get(OFFER_DESCRIPTION_COLUMN), equalTo(offer.getDescription()));
        assertThat(values.get(OFFER_OFFERING_ID_COLUMN), equalTo(offer.getOfferingIdentifier()));
        assertThat(values.get(OFFER_PRICE_COLUMN), equalTo(offer.getPrice()));
        assertThat(values.get(OFFER_CURRENCY_CODE_COLUMN), equalTo(offer.getCurrencyCode()));
        assertThat(values.get(OFFER_ACTIVE_START_DATE_COLUMN), equalTo(offer.getActiveStartDate()));
        assertThat(values.get(OFFER_ACTIVE_END_DATE_COLUMN), equalTo(offer.getActiveEndDate()));
        assertThat(values.get(OFFER_STATUS_CODE_COLUMN), equalTo(offer.getStatusCode()));
    }

    @Test
    public void findActiveOfferWhenItExists() {
        final ResponseEntity<Offer> response = restTemplate.exchange(
                createOfferUrlWithMerchantAndOfferIdentifier(),
                GET, null, Offer.class,
                MERCHANT_IDENTIFIER, OFFER_IDENTIFIER);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), notNullValue());

        final Offer offer = response.getBody();

        assertThat(offer.getOfferIdentifier(), equalTo(OFFER_IDENTIFIER));
        assertThat(offer.getMerchantIdentifier(), equalTo(MERCHANT_IDENTIFIER));
        assertThat(offer.getDescription(), equalTo("Interesting offer 1001"));
        assertThat(offer.getOfferingIdentifier(), equalTo("offering-id-1"));
        assertThat(offer.getPrice(), equalTo(new BigDecimal("12.34")));
        assertThat(offer.getCurrencyCode(), equalTo("GBP"));
        assertThat(offer.getActiveStartDate(), equalTo(Timestamp.valueOf("2018-01-01 00:00:00")));
        assertThat(offer.getActiveEndDate(), equalTo(Timestamp.valueOf("2028-01-31 23:59:59")));
        assertThat(offer.getStatusCode(), equalTo(OFFER_STATUS_CODE_ACTIVE));
    }

    @Test
    public void cancelOfferWhenItExist() {
        final ResponseEntity<Offer> response = restTemplate.exchange(
                createOfferUrlWithMerchantAndOfferIdentifier(),
                DELETE, null, Offer.class,
                MERCHANT_IDENTIFIER, OFFER_IDENTIFIER);

        assertThat(response.getStatusCode(), equalTo(OK));

        final String statusCode = findOfferStatusCode(namedParameterJdbcTemplate, OFFER_IDENTIFIER);

        assertThat(statusCode, equalTo(OFFER_STATUS_CODE_CANCELLED));
    }

    @Test
    public void findActiveOffers() {
        final ResponseEntity<Collection<Offer>> response = restTemplate.exchange(
                createOfferUrlWithMerchantIdentifier(),
                GET, null, new ParameterizedTypeReference<Collection<Offer>>() {
                },
                MERCHANT_IDENTIFIER);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), notNullValue());

        final Collection<Offer> offers = response.getBody();

        assertThat(offers.size(), equalTo(2));
    }

    private String createOfferUrlWithMerchantIdentifier() {
        return "http://localhost:" + port + "/v1/offers/{merchantIdentifier}";
    }

    private String createOfferUrlWithMerchantAndOfferIdentifier() {
        return "http://localhost:" + port + "/v1/offers/{merchantIdentifier}/{offerIdentifier}";
    }
}
