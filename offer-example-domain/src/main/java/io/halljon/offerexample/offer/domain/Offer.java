package io.halljon.offerexample.offer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Offer {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    //TODO: JH - start and end date from timestamp to local date time and consider status code as enum
    private String offerIdentifier;
    private String merchantIdentifier;
    private String description;
    private String offeringIdentifier;
    private BigDecimal price;
    private String currencyCode;
    private Timestamp activeStartDate;
    private Timestamp activeEndDate;
    private String statusCode;

    public String getOfferIdentifier() {
        return offerIdentifier;
    }

    public void setOfferIdentifier(final String offerIdentifier) {
        this.offerIdentifier = offerIdentifier;
    }

    public String getMerchantIdentifier() {
        return merchantIdentifier;
    }

    public void setMerchantIdentifier(final String merchantIdentifier) {
        this.merchantIdentifier = merchantIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getOfferingIdentifier() {
        return offeringIdentifier;
    }

    public void setOfferingIdentifier(final String offeringIdentifier) {
        this.offeringIdentifier = offeringIdentifier;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(final String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    public Timestamp getActiveStartDate() {
        return activeStartDate;
    }

    public void setActiveStartDate(final Timestamp activeStartDate) {
        this.activeStartDate = activeStartDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    public Timestamp getActiveEndDate() {
        return activeEndDate;
    }

    public void setActiveEndDate(final Timestamp activeEndDate) {
        this.activeEndDate = activeEndDate;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final String statusCode) {
        this.statusCode = statusCode;
    }
}
