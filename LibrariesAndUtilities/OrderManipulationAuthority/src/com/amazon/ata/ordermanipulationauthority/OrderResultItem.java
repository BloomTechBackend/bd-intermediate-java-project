package com.amazon.ata.ordermanipulationauthority;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an order item returned from the OrderManipulationAuthority.
 */
public final class OrderResultItem {

    private String customerOrderItemId;
    private int quantity;
    private ZonedDateTime approvalDate;
    private String orderId;
    private String merchantId;
    private List<OrderResultItemDetail> orderItemDetailList;
    private String asin;
    private String supplyCode;
    private String title;
    private ZonedDateTime supplyCodeDate;
    private boolean isConfidenceTracked;
    private int confidence;

    private OrderResultItem() {}

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

    public List<OrderResultItemDetail> getOrderItemDetailList() {
        return new ArrayList<>(orderItemDetailList);
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

    public int getConfidence() {
        return confidence;
    }

    /**
     * Returns a builder, suitable for constructing an OrderResultItem.
     * @return new Builder, ready to build an OrderResultItem
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String customerOrderItemId;
        private int quantity;
        private ZonedDateTime approvalDate;
        private String orderId;
        private String merchantId;
        private List<OrderResultItemDetail> orderItemDetailList;
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

        public Builder withOrderItemDetailList(List<OrderResultItemDetail> orderItemDetailList) {
            this.orderItemDetailList = new ArrayList<>(orderItemDetailList);
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
         * Builds a new OrderResultItem.
         * @return the newly minted OrderResultItem
         */
        public OrderResultItem build() {
            OrderResultItem orderResultItem = new OrderResultItem();
            orderResultItem.customerOrderItemId = customerOrderItemId;
            orderResultItem.quantity = quantity;
            orderResultItem.approvalDate = approvalDate;
            orderResultItem.orderId = orderId;
            orderResultItem.merchantId = merchantId;
            orderResultItem.orderItemDetailList = orderItemDetailList;
            orderResultItem.asin = asin;
            orderResultItem.supplyCode = supplyCode;
            orderResultItem.title = title;
            orderResultItem.supplyCodeDate = supplyCodeDate;
            orderResultItem.isConfidenceTracked = isConfidenceTracked;
            orderResultItem.confidence = confidence;
            return orderResultItem;
        }
    }
}
