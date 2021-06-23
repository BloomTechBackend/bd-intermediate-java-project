package com.amazon.ata.ordermanipulationauthority;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * An Amazon customer order. Construct via the Builder pattern
 *
 * @see OrderResult.Builder
 */
public final class OrderResult {

    private String orderId;
    private OrderCondition condition;
    private List<OrderResultItem> customerOrderItemList;
    private List<OrderShipment> orderShipmentList;
    private String customerId;
    private ZonedDateTime orderDate;
    private String shipOption;
    private String marketplaceId;

    private OrderResult() {}

    public String getOrderId() {
        return orderId;
    }

    public OrderCondition getCondition() {
        return condition;
    }

    public List<OrderResultItem> getCustomerOrderItemList() {
        return new ArrayList<>(customerOrderItemList);
    }

    public List<OrderShipment> getOrderShipmentList() {
        return new ArrayList<>(orderShipmentList);
    }

    public String getCustomerId() {
        return customerId;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public String getShipOption() {
        return shipOption;
    }

    public String getMarketplaceId() {
        return marketplaceId;
    }

    /**
     * Return a builder for building OrderResults.
     *
     * @return the new Builder ready to build
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for OrderResult.
     */
    public static class Builder {
        private String orderId;
        private OrderCondition condition;
        private List<OrderResultItem> customerOrderItemList;
        private List<OrderShipment> orderShipmentList;
        private String customerId;
        private ZonedDateTime orderDate;
        private String shipOption;
        private String marketplaceId;

        //CHECKSTYLE:OFF:JavadocMethod
        //CHECKSTYLE:OFF:HiddenField
        public Builder withOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder withCondition(OrderCondition condition) {
            this.condition = condition;
            return this;
        }

        public Builder withCustomerOrderItemList(List<OrderResultItem> customerOrderItemList) {
            this.customerOrderItemList = new ArrayList<>(customerOrderItemList);
            return this;
        }

        public Builder withOrderShipmentList(List<OrderShipment> orderShipmentList) {
            this.orderShipmentList = new ArrayList<>(orderShipmentList);
            return this;
        }

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder withOrderDate(ZonedDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder withShipOption(String shipOption) {
            this.shipOption = shipOption;
            return this;
        }

        public Builder withMarketplaceId(String marketplaceId) {
            this.marketplaceId = marketplaceId;
            return this;
        }
        //CHECKSTYLE:ON:HiddenField
        //CHECKSTYLE:ON:JavadocMethod

        /**
         * Builds a new OrderResult from the builder state.
         * @return newly minted OrderResult
         */
        public OrderResult build() {
            OrderResult orderResult = new OrderResult();
            orderResult.orderId = orderId;
            orderResult.condition = condition;
            orderResult.customerOrderItemList = customerOrderItemList;
            orderResult.orderShipmentList = orderShipmentList;
            orderResult.customerId = customerId;
            orderResult.orderDate = orderDate;
            orderResult.shipOption = shipOption;
            orderResult.marketplaceId = marketplaceId;
            return orderResult;
        }
    }
}
