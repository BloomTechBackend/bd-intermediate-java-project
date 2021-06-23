package com.amazon.ata.orderfulfillmentservice;

import java.time.ZonedDateTime;

/**
 * Represents a promise made by the Order Fulfillment Service.
 *
 * Promise field explanations:
 * * customerOrderId: the customer's order ID
 * * customerOrderItemId: the ID of the item in the customer's order that this Promise is for
 * * asin: ASIN of the item
 * * promiseQuantity: quantity of the item this Promise covers
 * * promiseLatestArrivalDate: timestamp representing the latest time the item(s) can arrive to meet promise
 * * promiseDataSource: the service consulted to compute the promise date
 * * promiseEffectiveDate: when this promise became the promise for this item
 * * planQualityTypeCode:
 * * isActive: true if this Promise is currently in effect and represents current promise to customer for this item;
 *             false otherwise
 * * promiseLatestShipDate: (internally facing) timestamp of latest time the shipment can be shipped and still make
 *                          customer promise
 * * fulfillmentSvsSubclassId: Meaningful code for the order fulfillment service identifying the type of order this
 *                             item belongs to
 * * promiseProvidedBy: the promise service that issued the promise (e.g. DPS, OFS...).
 */
public class OrderPromise {
    private String customerOrderId;
    private String customerOrderItemId;
    private String asin;
    private int promiseQuantity;
    private ZonedDateTime promiseLatestArrivalDate;
    private String promiseDataSource;
    private ZonedDateTime promiseEffectiveDate;
    private String planQualityTypeCode;
    private boolean isActive;
    private ZonedDateTime promiseLatestShipDate;
    private String fulfillmentSvcSubclassId;
    private String promiseProvidedBy;


    public String getCustomerOrderId() {
        return customerOrderId;
    }

    public String getCustomerOrderItemId() {
        return customerOrderItemId;
    }

    public String getAsin() {
        return asin;
    }

    public int getPromiseQuantity() {
        return promiseQuantity;
    }

    public ZonedDateTime getPromiseLatestArrivalDate() {
        return promiseLatestArrivalDate;
    }

    public String getPromiseDataSource() {
        return promiseDataSource;
    }

    public ZonedDateTime getPromiseEffectiveDate() {
        return promiseEffectiveDate;
    }

    public String getPlanQualityTypeCode() {
        return planQualityTypeCode;
    }

    public boolean isActive() {
        return isActive;
    }

    public ZonedDateTime getPromiseLatestShipDate() {
        return promiseLatestShipDate;
    }

    public String getFulfillmentSvcSubclassId() {
        return fulfillmentSvcSubclassId;
    }

    public String getPromiseProvidedBy() {
        return promiseProvidedBy;
    }

    @Override
    public String toString() {
        return "OrderPromise{" +
               "customerOrderId='" + customerOrderId + '\'' +
               ", customerOrderItemId='" + customerOrderItemId + '\'' +
               ", asin='" + asin + '\'' +
               ", promiseQuantity=" + promiseQuantity +
               ", promiseLatestArrivalDate=" + promiseLatestArrivalDate +
               ", promiseDataSource='" + promiseDataSource + '\'' +
               ", promiseEffectiveDate=" + promiseEffectiveDate +
               ", planQualityTypeCode='" + planQualityTypeCode + '\'' +
               ", isActive=" + isActive +
               ", promiseLatestShipDate=" + promiseLatestShipDate +
               ", fulfillmentSvcSubclassId='" + fulfillmentSvcSubclassId + '\'' +
               ", promiseProvidedBy='" + promiseProvidedBy + '\'' +
               '}';
    }

    /**
     * Returns a builder suitable for constructing an OrderPromise.
     * @return Builder object ready to build an OrderPromise
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder object for building new OrderPromises.
     */
    public static class Builder {
        private String customerOrderId;
        private String customerOrderItemId;
        private String asin;
        private int promiseQuantity;
        private ZonedDateTime promiseLatestArrivalDate;
        private String promiseDataSource;
        private ZonedDateTime promiseEffectiveDate;
        private String planQualityTypeCode;
        private boolean isActive;
        private ZonedDateTime promiseLatestShipDate;
        private String fulfillmentSvcSubclassId;
        private String promiseProvidedBy;

        //CHECKSTYLE:OFF:JavadocMethod
        //CHECKSTYLE:OFF:HiddenField
        public Builder withCustomerOrderId(String customerOrderId) {
            this.customerOrderId = customerOrderId;
            return this;
        }

        public Builder withCustomerOrderItemId(String customerOrderItemId) {
            this.customerOrderItemId = customerOrderItemId;
            return this;
        }

        public Builder withAsin(String asin) {
            this.asin = asin;
            return this;
        }

        public Builder withPromiseQuantity(int promiseQuantity) {
            this.promiseQuantity = promiseQuantity;
            return this;
        }

        public Builder withPromiseLatestArrivalDate(ZonedDateTime promiseLatestArrivalDate) {
            this.promiseLatestArrivalDate = promiseLatestArrivalDate;
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

        public Builder withPlanQualityTypeCode(String planQualityTypeCode) {
            this.planQualityTypeCode = planQualityTypeCode;
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

        public Builder withFulfillmentSvcSubclassId(String fulfillmentSvcSubclassId) {
            this.fulfillmentSvcSubclassId = fulfillmentSvcSubclassId;
            return this;
        }

        public Builder withPromiseProvidedBy(String promiseProvidedBy) {
            this.promiseProvidedBy = promiseProvidedBy;
            return this;
        }

        //CHECKSTYLE:ON:HiddenField
        //CHECKSTYLE:ON:JavadocMethod

        /**
         * constructs a new OrderPromise from the current builder state.
         * @return OrderPromise, populated with values from the builder's state
         */
        public OrderPromise build() {
            OrderPromise orderPromise = new OrderPromise();
            orderPromise.customerOrderId = customerOrderId;
            orderPromise.customerOrderItemId = customerOrderItemId;
            orderPromise.asin = asin;
            orderPromise.promiseQuantity = promiseQuantity;
            orderPromise.promiseLatestArrivalDate = promiseLatestArrivalDate;
            orderPromise.promiseDataSource = promiseDataSource;
            orderPromise.promiseEffectiveDate = promiseEffectiveDate;
            orderPromise.planQualityTypeCode = planQualityTypeCode;
            orderPromise.isActive = isActive;
            orderPromise.promiseLatestShipDate = promiseLatestShipDate;
            orderPromise.fulfillmentSvcSubclassId = fulfillmentSvcSubclassId;
            orderPromise.promiseProvidedBy = promiseProvidedBy;
            return orderPromise;
        }
    }
}
