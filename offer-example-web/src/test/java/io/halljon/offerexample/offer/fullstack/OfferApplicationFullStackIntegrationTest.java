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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static io.halljon.offerexample.offer.domain.OfferTestUtils.createPopulatedOfferWithKnownValues;
import static org.hamcrest.core.IsEqual.equalTo;
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

    @LocalServerPort
    private int port;

    @Test
    public void createNewOffer() {
        final Offer offer = createPopulatedOfferWithKnownValues();
        final HttpEntity<Offer> request = new HttpEntity<>(offer);

        final ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/v1/offers/abc/", POST, request, String.class);

        assertThat(response.getStatusCode(), equalTo(OK));
    }
}
