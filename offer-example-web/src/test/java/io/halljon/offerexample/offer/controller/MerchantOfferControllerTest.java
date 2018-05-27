package io.halljon.offerexample.offer.controller;

import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.service.OfferService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;

import static io.halljon.offerexample.offer.domain.OfferTestUtils.createPopulatedOfferWithKnownValues;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.toJson;
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
    private final MediaType contentType =
            new MediaType(APPLICATION_JSON.getType(), APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService mockOfferService;

    @Captor
    private ArgumentCaptor<Offer> captorOffer;

    @After
    public void afterEachTest() {
        verifyNoMoreInteractions(mockOfferService);
    }

    @Test
    public void createNewOffer()
            throws Exception {

        final Offer offer = createPopulatedOfferWithKnownValues();
        final String merchantIdentifier = "merchant-id-12345";
        final String expectedOfferIdentifier = "offer-id-12345";

        when(mockOfferService
                .createNewOffer(eq(merchantIdentifier), any(Offer.class))
        ).thenReturn(expectedOfferIdentifier);

        final MvcResult mvcResult = mockMvc.perform(
                post("/v1/offers/{merchantIdentifier}", merchantIdentifier)
                        .contentType(contentType)
                        .content(toJson(offer)))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), equalTo(OK.value()));
        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(expectedOfferIdentifier));

        verify(mockOfferService)
                .createNewOffer(eq(merchantIdentifier), captorOffer.capture());

        assertThat(captorOffer.getValue().getDescription(), equalTo(offer.getDescription()));
        assertThat(captorOffer.getValue().getOfferingIdentifier(), equalTo(offer.getOfferingIdentifier()));
        assertThat(captorOffer.getValue().getPrice(), equalTo(offer.getPrice()));
        assertThat(captorOffer.getValue().getCurrencyCode(), equalTo(offer.getCurrencyCode()));
        assertThat(captorOffer.getValue().getActiveStartDate(), equalTo(offer.getActiveStartDate()));
        assertThat(captorOffer.getValue().getActiveEndDate(), equalTo(offer.getActiveEndDate()));
        assertThat(captorOffer.getValue().getStatusCode(), equalTo(offer.getStatusCode()));
    }
}
