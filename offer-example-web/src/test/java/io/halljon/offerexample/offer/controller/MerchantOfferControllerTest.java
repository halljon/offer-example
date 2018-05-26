package io.halljon.offerexample.offer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.service.OfferService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(MerchantOfferController.class)
public class MerchantOfferControllerTest {
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MediaType contentType =
            new MediaType(APPLICATION_JSON.getType(), APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @After
    public void afterEachTest() {
        verifyNoMoreInteractions(offerService);
    }

    @Test
    public void postOfferOk()
            throws Exception {

        final String merchantIdentifier = "merchant-id-12345";
        final Offer offer = createPopulatedOffer();
        final String expectedOfferIdentifier = "offer-id-12345";

        when(offerService
                .saveOffer(eq(merchantIdentifier), any(Offer.class))
        ).thenReturn(expectedOfferIdentifier);

        final MvcResult mvcResult = mockMvc.perform(
                post("/v1/offers/" + merchantIdentifier)
                        .contentType(contentType)
                        .content(asJson(offer)))
                .andReturn();

        verify(offerService)
                .saveOffer(eq(merchantIdentifier), any(Offer.class));

        assertThat(mvcResult.getResponse().getStatus(), equalTo(OK.value()));
        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(expectedOfferIdentifier));
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

    private String asJson(final Offer offer)
            throws JsonProcessingException {

        return objectMapper.writeValueAsString(offer);
    }
}
