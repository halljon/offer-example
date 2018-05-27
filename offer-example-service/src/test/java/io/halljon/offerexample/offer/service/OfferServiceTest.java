package io.halljon.offerexample.offer.service;

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
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static io.halljon.offerexample.offer.common.OfferConst.DEFAULT_ZONE_ID;
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
    private static final String MERCHANT_IDENTIFIER = "merchant-identifier";
    private static final String OFFER_IDENTIFIER = "offer-identifier";
    private static final String SPECIFIC_EXCEPTION_WAS_EXPECTED_BUT_DID_NOT_OCCUR =
            "A specific exception was expected, but did not occur";

    private final Offer offer = new Offer();

    @Mock
    private OfferRepository mockOfferRepository;

    @Mock
    private IdentifierGenerator mockGenerator;

    @Mock
    private DateService mockDateService;

    private OfferService offerService;

    @Before
    public void beforeEachTest() {
        offerService = new OfferServiceImpl(mockOfferRepository, mockGenerator, mockDateService);
    }

    @After
    public void afterEachTest() {
        verifyNoMoreInteractions(
                mockOfferRepository,
                mockGenerator,
                mockDateService
        );
    }

    @Test
    public void createNewOffer() {
        when(mockGenerator
                .generateIdentifier()
        ).thenReturn(
                OFFER_IDENTIFIER
        );

        doNothing().when(mockOfferRepository)
                .saveNewOffer(offer);

        final String identifier = offerService.createNewOffer(MERCHANT_IDENTIFIER, offer);

        assertThat(identifier, equalTo(OFFER_IDENTIFIER));
        assertThat(offer.getOfferIdentifier(), equalTo(OFFER_IDENTIFIER));
        assertThat(offer.getMerchantIdentifier(), equalTo(MERCHANT_IDENTIFIER));

        verify(mockGenerator)
                .generateIdentifier();

        verify(mockOfferRepository)
                .saveNewOffer(offer);
    }

    @Test
    public void createNewOfferWhenRepositoryThrowsException() {
        when(mockGenerator
                .generateIdentifier()
        ).thenReturn(
                OFFER_IDENTIFIER
        );

        doThrow(DataIntegrityViolationException.class).when(mockOfferRepository)
                .saveNewOffer(offer);

        try {
            offerService.createNewOffer(MERCHANT_IDENTIFIER, offer);

            fail(SPECIFIC_EXCEPTION_WAS_EXPECTED_BUT_DID_NOT_OCCUR);
        } catch (DataIntegrityViolationException e) {
            verify(mockGenerator)
                    .generateIdentifier();

            verify(mockOfferRepository)
                    .saveNewOffer(offer);
        } catch (Exception e) {
            fail(SPECIFIC_EXCEPTION_WAS_EXPECTED_BUT_DID_NOT_OCCUR);
        }
    }

    @Test
    public void findActiveOffer() {
        final LocalDateTime dateTime = LocalDateTime.now(DEFAULT_ZONE_ID);
        final Timestamp timestamp = Timestamp.valueOf(dateTime);
        final Offer offer = new Offer();

        when(mockDateService
                .getCurrentDateTime()
        ).thenReturn(
                dateTime
        );

        when(mockOfferRepository.
                findActiveOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER, timestamp)
        ).thenReturn(
                offer
        );

        final Offer offerFound = offerService.findActiveOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER);

        assertThat(offerFound, equalTo(offer));

        verify(mockDateService)
                .getCurrentDateTime();

        verify(mockOfferRepository)
                .findActiveOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER, timestamp);
    }

    @Test
    public void findActiveOfferWhenRepositoryThrowsException() {
        final LocalDateTime dateTime = LocalDateTime.now(DEFAULT_ZONE_ID);
        final Timestamp timestamp = Timestamp.valueOf(dateTime);

        when(mockDateService
                .getCurrentDateTime()
        ).thenReturn(
                dateTime
        );

        doThrow(EmptyResultDataAccessException.class).when(mockOfferRepository)
                .findActiveOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER, timestamp);

        try {
            offerService.findActiveOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER);
        } catch (EmptyResultDataAccessException e) {
            verify(mockDateService)
                    .getCurrentDateTime();

            verify(mockOfferRepository)
                    .findActiveOffer(MERCHANT_IDENTIFIER, OFFER_IDENTIFIER, timestamp);
        } catch (Exception e) {
            fail(SPECIFIC_EXCEPTION_WAS_EXPECTED_BUT_DID_NOT_OCCUR);
        }
    }
}
