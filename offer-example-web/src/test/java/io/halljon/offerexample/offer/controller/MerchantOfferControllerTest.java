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

import static io.halljon.offerexample.offer.controller.OfferControllerTestUtils.createOfferUrlTemplateWithMerchantAndOfferIdentifiers;
import static io.halljon.offerexample.offer.controller.OfferControllerTestUtils.createOfferUrlTemplateWithMerchantIdentifier;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.createPopulatedOfferWithKnownValues;
import static io.halljon.offerexample.offer.domain.OfferTestUtils.toJson;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(MerchantOfferController.class)
public class MerchantOfferControllerTest {
    private static final String MERCHANT_IDENTIFIER = "merchant-id-12345";
    private static final String OFFER_IDENTIFIER = "offer-id-12345";

    private static final MediaType CONTENT_TYPE =
            new MediaType(APPLICATION_JSON.getType(), APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

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

        when(mockOfferService
                .createNewOffer(eq(MERCHANT_IDENTIFIER), any(Offer.class))
        ).thenReturn(
                OFFER_IDENTIFIER
        );

        final MvcResult result = mockMvc.perform(
                post(createOfferUrlTemplateWithMerchantIdentifier(), MERCHANT_IDENTIFIER)
                        .contentType(CONTENT_TYPE)
                        .content(toJson(offer))
        ).andReturn();

        assertThat(result.getResponse().getStatus(), equalTo(OK.value()));
        assertThat(result.getResponse().getContentAsString(), equalTo(OFFER_IDENTIFIER));

        verify(mockOfferService)
                .createNewOffer(eq(MERCHANT_IDENTIFIER), captorOffer.capture());

        assertThat(captorOffer.getValue().getDescription(), equalTo(offer.getDescription()));
        assertThat(captorOffer.getValue().getOfferingIdentifier(), equalTo(offer.getOfferingIdentifier()));
        assertThat(captorOffer.getValue().getPrice(), equalTo(offer.getPrice()));
        assertThat(captorOffer.getValue().getCurrencyCode(), equalTo(offer.getCurrencyCode()));
        assertThat(captorOffer.getValue().getActiveStartDate(), equalTo(offer.getActiveStartDate()));
        assertThat(captorOffer.getValue().getActiveEndDate(), equalTo(offer.getActiveEndDate()));
        assertThat(captorOffer.getValue().getStatusCode(), equalTo(offer.getStatusCode()));
    }

    @Test
    public void cancelOfferWhenItExists()
            throws Exception {

        when(mockOfferService
                .cancelOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER)
        ).thenReturn(
                true
        );

        final MvcResult result = mockMvc.perform(
                delete(createOfferUrlTemplateWithMerchantAndOfferIdentifiers(), MERCHANT_IDENTIFIER, OFFER_IDENTIFIER)
        ).andReturn();

        assertThat(result.getResponse().getStatus(), equalTo(OK.value()));
        verify(mockOfferService)
                .cancelOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER);
    }

    @Test
    public void cancelOfferWhenItDoesNotExist()
            throws Exception {

        when(mockOfferService
                .cancelOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER)
        ).thenReturn(
                false
        );

        final MvcResult result = mockMvc.perform(
                delete(createOfferUrlTemplateWithMerchantAndOfferIdentifiers(), MERCHANT_IDENTIFIER, OFFER_IDENTIFIER)
        ).andReturn();

        assertThat(result.getResponse().getStatus(), equalTo(NOT_FOUND.value()));

        verify(mockOfferService)
                .cancelOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER);
    }
}
