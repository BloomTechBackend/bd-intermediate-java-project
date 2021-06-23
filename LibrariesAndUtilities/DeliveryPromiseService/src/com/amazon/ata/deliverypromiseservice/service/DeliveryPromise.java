package com.amazon.ata.deliverypromiseservice.service;

import java.time.ZonedDateTime;

/**
 * An Amazon delivery promise.
 */
public class DeliveryPromise {
    private String fulfillmentSvcSubclassId;
    private String customerOrderItemId;
    private int promiseQuantity;
    private String customerOrderId;
    private String promiseDataSource;
    private ZonedDateTime promiseEffectiveDate;
    private boolean isActive;
    private ZonedDateTime promiseLatestShipDate;
    private String promiseProvidedBy;
    private String asin;
    private String planQualityTypeCode;
    private ZonedDateTime promiseLatestArrivalDate;

    public String getFulfillmentSvcSubclassId() {
        return fulfillmentSvcSubclassId;
    }

    public String getCustomerOrderItemId() {
        return customerOrderItemId;
    }

    public int getPromiseQuantity() {
        return promiseQuantity;
    }

    public String getCustomerOrderId() {
        return customerOrderId;
    }

    public String getPromiseDataSource() {
        return promiseDataSource;
    }

    public ZonedDateTime getPromiseEffectiveDate() {
        return promiseEffectiveDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public ZonedDateTime getPromiseLatestShipDate() {
        return promiseLatestShipDate;
    }

    public String getPromiseProvidedBy() {
        return promiseProvidedBy;
    }

    public String getAsin() {
        return asin;
    }

    public String getPlanQualityTypeCode() {
        return planQualityTypeCode;
    }

    public ZonedDateTime getPromiseLatestArrivalDate() {
        return promiseLatestArrivalDate;
    }

    /**
     * Returns a Builder suitable for building a DeliveryPromise.
     *
     * @return a new Builder for building a DeliveryPromise
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for building DeliveryPromises.
     */
    public static class Builder {
        private String fulfillmentSvcSubclassId;
        private String customerOrderItemId;
        private int promiseQuantity;
        private String customerOrderId;
        private String promiseDataSource;
        private ZonedDateTime promiseEffectiveDate;
        private boolean isActive;
        private ZonedDateTime promiseLatestShipDate;
        private String promiseProvidedBy;
        private String asin;
        private String planQualityTypeCode;
        private ZonedDateTime promiseLatestArrivalDate;

        //CHECKSTYLE:OFF:JavadocMethod
        //CHECKSTYLE:OFF:HiddenField
        public Builder withFulfillmentSvcSubclassId(String fulfillmentSvcSubclassId) {
            this.fulfillmentSvcSubclassId = fulfillmentSvcSubclassId;
            return this;
        }

        public Builder withCustomerOrderItemId(String customerOrderItemId) {
            this.customerOrderItemId = customerOrderItemId;
            return this;
        }

        public Builder withPromiseQuantity(int promiseQuantity) {
            this.promiseQuantity = promiseQuantity;
            return this;
        }

        public Builder withCustomerOrderId(String customerOrderId) {
            this.customerOrderId = customerOrderId;
            return this;
        }

        public Builder withPromiseDataSource(String promiseDataSource) {
            this.promiseDataSource = promiseDataSource;
            return this;
        }

        public Builder withPromiseEffectiveDate(ZonedDateTime promiseEffectiveDate) {
            this.promiseEffectiveDate = promiseEffectiveDate;
            return this;
        }

        public Builder withIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder withPromiseLatestShipDate(ZonedDateTime promiseLatestShipDate) {
            this.promiseLatestShipDate = promiseLatestShipDate;
            return this;
        }

        public Builder withPromiseProvidedBy(String promiseProvidedBy) {
            this.promiseProvidedBy = promiseProvidedBy;
            return this;
        }

        public Builder withAsin(String asin) {
            this.asin = asin;
            return this;
        }

        public Builder withPlanQualityTypeCode(String planQualityTypeCode) {
            this.planQualityTypeCode = planQualityTypeCode;
            return this;
        }

        public Builder withPromiseLatestArrivalDate(ZonedDateTime promiseLatestArrivalDate) {
            this.promiseLatestArrivalDate = promiseLatestArrivalDate;
            return this;
        }
        //CHECKSTYLE:ON:HiddenField
        //CHECKSTYLE:ON:JavadocMethod

        /**
         * Builds a new DeliveryPromise based on current builder state.
         * @return a new DeliveryPromise
         */
        public DeliveryPromise build() {
            DeliveryPromise deliveryPromise = new DeliveryPromise();
            deliveryPromise.fulfillmentSvcSubclassId = fulfillmentSvcSubclassId;
            deliveryPromise.customerOrderItemId = customerOrderItemId;
            deliveryPromise.promiseQuantity = promiseQuantity;
            deliveryPromise.customerOrderId = customerOrderId;
            deliveryPromise.promiseDataSource = promiseDataSource;
            deliveryPromise.promiseEffectiveDate = promiseEffectiveDate;
            deliveryPromise.isActive = isActive;
            deliveryPromise.promiseLatestShipDate = promiseLatestShipDate;
            deliveryPromise.promiseProvidedBy = promiseProvidedBy;
            deliveryPromise.asin = asin;
            deliveryPromise.planQualityTypeCode = planQualityTypeCode;
            deliveryPromise.promiseLatestArrivalDate = promiseLatestArrivalDate;
            return deliveryPromise;
        }
    }
}
