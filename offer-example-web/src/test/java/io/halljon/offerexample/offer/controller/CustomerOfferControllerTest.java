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

import java.nio.charset.Charset;

import static io.halljon.offerexample.offer.domain.OfferTestUtils.createPopulatedOfferWithKnownValues;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerOfferController.class)
public class CustomerOfferControllerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final MediaType contentType =
            new MediaType(APPLICATION_JSON.getType(), APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService mockOfferService;

    @After
    public void afterEachTest() {
        verifyNoMoreInteractions(mockOfferService);
    }

    @Test
    public void findActiveOffer()
            throws Exception {

        final String offerIdentifier = "offer-id-12345";
        final String merchantIdentifier = "merchant-id-12345";
        final Offer offer = createPopulatedOfferWithKnownValues();

        when(mockOfferService
                .findActiveOffer(merchantIdentifier, offerIdentifier)
        ).thenReturn(
                offer
        );

        final MvcResult mvcResult = mockMvc.perform(
                get("/v1/offers/{merchantIdentifier}/{offerIdentifier}", merchantIdentifier, offerIdentifier))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), equalTo(OK.value()));
        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(toJson(offer)));

        verify(mockOfferService)
                .findActiveOffer(merchantIdentifier, offerIdentifier);
    }

    private String toJson(final Offer offer)
            throws JsonProcessingException {

        return OBJECT_MAPPER.writeValueAsString(offer);
    }
}
