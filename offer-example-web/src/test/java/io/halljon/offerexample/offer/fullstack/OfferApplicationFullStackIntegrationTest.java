package io.halljon.offerexample.offer.fullstack;

import io.halljon.offerexample.offer.OfferApplication;
import io.halljon.offerexample.offer.domain.Offer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static io.halljon.offerexample.offer.domain.OfferTestUtils.createPopulatedOfferWithKnownValues;
import static java.util.Collections.singletonMap;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApplication.class, webEnvironment = RANDOM_PORT)
@Sql(scripts = "classpath:full-stack-test-preparation.sql", executionPhase = BEFORE_TEST_METHOD)
public class OfferApplicationFullStackIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void createNewOffer() {
        final String merchantIdentifier = "full-stack-merchant-id";
        final Offer offer = createPopulatedOfferWithKnownValues();
        final HttpEntity<Offer> request = new HttpEntity<>(offer);

        final ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/v1/offers/" + merchantIdentifier, POST, request, String.class);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), notNullValue());

        final String identifierOfCreatedOffer = response.getBody();

        final Map<String, Object> values = namedParameterJdbcTemplate.queryForMap(
                "SELECT * FROM offer WHERE offer_id = :offer_id",
                singletonMap("offer_id", identifierOfCreatedOffer));

        assertThat(values.get("offer_id"), equalTo(identifierOfCreatedOffer));
        assertThat(values.get("merchant_id"), equalTo(merchantIdentifier));
        assertThat(values.get("description"), equalTo(offer.getDescription()));
        assertThat(values.get("offering_id"), equalTo(offer.getOfferingIdentifier()));
        assertThat(values.get("price"), equalTo(offer.getPrice()));
        assertThat(values.get("currency_code"), equalTo(offer.getCurrencyCode()));
        assertThat(values.get("active_start_date"), equalTo(offer.getActiveStartDate()));
        assertThat(values.get("active_end_date"), equalTo(offer.getActiveEndDate()));
        assertThat(values.get("status_code"), equalTo(offer.getStatusCode()));
    }
}
