package com.amazon.ata.deliveringonourpromise.data;

import java.time.ZonedDateTime;

/**
 * Data object from shared data provider that represents an Order item from an Order.
 *
 * @see OrderDatastore
 * @see OrderData
 */
public final class OrderItemData {
    private String customerOrderItemId;
    private int quantity;
    private ZonedDateTime approvalDate;
    private String orderId;
    private String merchantId;
    private String asin;
    private String supplyCode;
    private String title;
    private ZonedDateTime supplyCodeDate;
    private boolean isConfidenceTracked;
    private int confidence;

    private OrderItemData() {}

    /**
     * Sets the order ID, allowing overwriting to match the requested order ID.
     * @param orderId The new order ID
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerOrderItemId() {
        return customerOrderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public ZonedDateTime getApprovalDate() {
        return approvalDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getAsin() {
        return asin;
    }

    public String getSupplyCode() {
        return supplyCode;
    }

    public String getTitle() {
        return title;
    }

    public ZonedDateTime getSupplyCodeDate() {
        return supplyCodeDate;
    }

    public boolean isConfidenceTracked() {
        return isConfidenceTracked;
    }

    public void setConfidenceTracked(final boolean confidenceTracked) {
        isConfidenceTracked = confidenceTracked;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(final int confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "OrderItemData{"
               + "customerOrderItemId='" + customerOrderItemId + '\''
               + ", quantity=" + quantity
               + ", approvalDate=" + approvalDate
               + ", orderId='" + orderId + '\''
               + ", merchantId='" + merchantId + '\''
               + ", asin='" + asin + '\''
               + ", supplyCode='" + supplyCode + '\''
               + ", title='" + title + '\''
               + ", supplyCodeDate=" + supplyCodeDate
               + '}';
    }

    /**
     * Returns a fresh builder for building OrderItemData.
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing OrderItemData via the builder pattern.
     */
    public static class Builder {
        private String customerOrderItemId;
        private int quantity;
        private ZonedDateTime approvalDate;
        private String orderId;
        private String merchantId;
        private String asin;
        private String supplyCode;
        private String title;
        private ZonedDateTime supplyCodeDate;
        private boolean isConfidenceTracked;
        private int confidence;

        //CHECKSTYLE:OFF:JavadocMethod
        //CHECKSTYLE:OFF:HiddenField
        public Builder withCustomerOrderItemId(String customerOrderItemId) {
            this.customerOrderItemId = customerOrderItemId;
            return this;
        }

        public Builder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder withApprovalDate(ZonedDateTime approvalDate) {
            this.approvalDate = approvalDate;
            return this;
        }

        public Builder withOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder withMerchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public Builder withAsin(String asin) {
            this.asin = asin;
            return this;
        }

        public Builder withSupplyCode(String supplyCode) {
            this.supplyCode = supplyCode;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withSupplyCodeDate(ZonedDateTime supplyCodeDate) {
            this.supplyCodeDate = supplyCodeDate;
            return this;
        }

        public Builder withIsConfidenceTracked(boolean isConfidenceTracked) {
            this.isConfidenceTracked = isConfidenceTracked;
            return this;
        }

        public Builder withConfidence(int confidence) {
            this.confidence = confidence;
            return this;
        }
        //CHECKSTYLE:ON:HiddenField
        //CHECKSTYLE:ON:JavadocMethod

        /**
         * Builds the OrderItemData from provided fields.
         * @return populated OrderItemData object
         */
        public OrderItemData build() {
            OrderItemData data = new OrderItemData();

            data.customerOrderItemId = customerOrderItemId;
            data.quantity = quantity;
            data.approvalDate = approvalDate;
            data.orderId = orderId;
            data.merchantId = merchantId;
            data.asin = asin;
            data.supplyCode = supplyCode;
            data.title = title;
            data.supplyCodeDate = supplyCodeDate;
            data.isConfidenceTracked = isConfidenceTracked;
            data.confidence = confidence;

            return data;
        }
    }
}
