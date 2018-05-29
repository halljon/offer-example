package io.halljon.offerexample.offer.domain;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static io.halljon.offerexample.offer.domain.OfferTestUtils.createPopulatedOfferWithKnownValues;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class OfferTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private final Offer offer = createPopulatedOfferWithKnownValues();

    @Test
    public void validateWhenOfferIdentifierIsAllowedNullable() {
        offer.setOfferIdentifier(null);

        final Set<ConstraintViolation<Offer>> constraintViolations = validator.validate(offer);

        assertThat(constraintViolations.size(), equalTo(0));
    }

    @Test
    public void validateWhenOfferFullyPopulated() {
        final Set<ConstraintViolation<Offer>> constraintViolations = validator.validate(offer);

        assertThat(constraintViolations.size(), equalTo(0));
    }

    @Test
    public void validateWhenOfferMandatoryValuesNotPopulated() {
        final Set<ConstraintViolation<Offer>> constraintViolations = validator.validate(new Offer());

        assertThat(constraintViolations.size(), equalTo(8));
    }
}
