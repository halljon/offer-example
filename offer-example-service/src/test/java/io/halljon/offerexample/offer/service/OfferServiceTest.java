package io.halljon.offerexample.offer.service;

import io.halljon.offerexample.identifier.IdentifierGenerator;
import io.halljon.offerexample.offer.domain.Offer;
import io.halljon.offerexample.offer.repository.OfferRepository;
import io.halljon.offerexample.offer.service.impl.OfferServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {
    private static final String EXPECTED_MERCHANT_IDENTIFIER = "merchant-identifier";
    private static final String EXPECTED_OFFER_IDENTIFIER = "offer-identifier";

    private final Offer offer = new Offer();

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private IdentifierGenerator generator;

    private OfferService offerService;


    @Before
    public void beforeEachTest() {
        offerService = new OfferServiceImpl(offerRepository, generator);
    }

    @After
    public void afterEachTest() {
        verifyNoMoreInteractions(
                offerRepository,
                generator
        );
    }

    @Test
    public void saveOffer() {
        when(generator
                .generateIdentifier()
        ).thenReturn(
                EXPECTED_OFFER_IDENTIFIER
        );

        doNothing().when(offerRepository)
                .saveOffer(offer);

        final String identifier = offerService.saveOffer(EXPECTED_MERCHANT_IDENTIFIER, offer);

        assertThat(identifier, equalTo(EXPECTED_OFFER_IDENTIFIER));
        assertThat(offer.getOfferIdentifier(), equalTo(EXPECTED_OFFER_IDENTIFIER));
        assertThat(offer.getMerchantIdentifier(), equalTo(EXPECTED_MERCHANT_IDENTIFIER));

        verify(generator)
                .generateIdentifier();

        verify(offerRepository)
                .saveOffer(offer);
    }

    @Test
    public void saveOfferWhenRepositoryThrowsExceptio() {
        when(generator
                .generateIdentifier()
        ).thenReturn(
                EXPECTED_OFFER_IDENTIFIER
        );

        doThrow(DataIntegrityViolationException.class).when(offerRepository)
                .saveOffer(offer);

        try {
            offerService.saveOffer(EXPECTED_MERCHANT_IDENTIFIER, offer);

            fail("Exception was expected");
        } catch (Exception e) {
            verify(generator)
                    .generateIdentifier();

            verify(offerRepository)
                    .saveOffer(offer);
        }
    }
}
