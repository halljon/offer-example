package io.halljon.offerexample.offer.controller;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.service.OfferService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static io.halljon.offerexample.offer.domain.OfferTestUtils.createPopulatedOfferWithKnownValues;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.toJson;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerOfferController.class)
public class CustomerOfferControllerTest {
    private static final String MERCHANT_IDENTIFIER = "merchant-id-12345";
    private static final String OFFER_IDENTIFIER = "offer-id-12345";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService mockOfferService;

    @After
    public void afterEachTest() {
        verifyNoMoreInteractions(mockOfferService);
    }

    @Test
    public void findActiveOfferWhenItExists()
            throws Exception {

        final Offer offer = createPopulatedOfferWithKnownValues();

        when(mockOfferService
                .findActiveOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER)
        ).thenReturn(
                Optional.of(offer)
        );

        final MvcResult mvcResult = mockMvc.perform(
                get("/v1/offers/{merchantIdentifier}/{offerIdentifier}", MERCHANT_IDENTIFIER, OFFER_IDENTIFIER))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), equalTo(OK.value()));
        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(toJson(offer)));

        verify(mockOfferService)
                .findActiveOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER);
    }

    @Test
    public void findActiveOfferWhenItDoesNotExist()
            throws Exception {

        when(mockOfferService
                .findActiveOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER)
        ).thenReturn(
                Optional.empty()
        );

        final MvcResult mvcResult = mockMvc.perform(
                get("/v1/offers/{merchantIdentifier}/{offerIdentifier}", MERCHANT_IDENTIFIER, OFFER_IDENTIFIER))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), equalTo(NOT_FOUND.value()));

        verify(mockOfferService)
                .findActiveOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER);
    }
}
