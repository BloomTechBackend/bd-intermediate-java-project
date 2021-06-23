package com.amazon.ata.deliveringonourpromise.data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data object from shared data provider that represents an Order.
 *
 * @see OrderDatastore
 */
public final class OrderData {
    private String orderId;
    private String customerId;
    private String marketplaceId;
    private int condition;
    private List<OrderItemData> customerOrderItemList;
    private List<OrderShipmentData> customerShipments;
    private String shipOption;
    private ZonedDateTime orderDate;

    private OrderData() {
    }

    /**
     * Allow setting the orderId after building to match incoming order ID requested.
     *
     * @param orderId the new order ID for the data object
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Allow replacing order item list for flexibility.
     *
     * @param customerOrderItemList replacement {@code List<OrderItemData>}
     */
    public void setCustomerOrderItemList(List<OrderItemData> customerOrderItemList) {
        this.customerOrderItemList = customerOrderItemList;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getMarketplaceId() {
        return marketplaceId;
    }

    public int getCondition() {
        return condition;
    }

    public List<OrderItemData> getCustomerOrderItemList() {
        return new ArrayList<>(customerOrderItemList);
    }

    public List<OrderShipmentData> getCustomerShipments() {
        return new ArrayList<>(customerShipments);
    }

    public String getShipOption() {
        return shipOption;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "OrderData{"
               + "orderId='" + orderId + '\''
               + ", customerId='" + customerId + '\''
               + ", marketplaceId='" + marketplaceId + '\''
               + ", condition=" + condition
               + ", customerOrderItemList=" + customerOrderItemList
               + ", customerShipments=" + customerShipments
               + ", shipOption='" + shipOption + '\''
               + ", orderDate=" + orderDate
               + '}';
    }

    /**
     * Returns a fresh builder for building OrderData.
     *
     * @return a new Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing OrderData via the builder pattern.
     */
    public static class Builder {
        private String orderId;
        private String customerId;
        private String marketplaceId;
        private int condition;
        private List<OrderItemData> customerOrderItemList;
        private List<OrderShipmentData> customerShipments;
        private String shipOption;
        private ZonedDateTime orderDate;

        //CHECKSTYLE:OFF:JavadocMethod
        //CHECKSTYLE:OFF:HiddenField
        public Builder withOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder withMarketplaceId(String marketplaceId) {
            this.marketplaceId = marketplaceId;
            return this;
        }

        public Builder withCondition(int condition) {
            this.condition = condition;
            return this;
        }

        public Builder withCustomerOrderItemList(List<OrderItemData> customerOrderItemList) {
            this.customerOrderItemList = new ArrayList<>(customerOrderItemList);
            return this;
        }

        public Builder withCustomerShipments(List<OrderShipmentData> customerShipments) {
            this.customerShipments = new ArrayList<>(customerShipments);
            return this;
        }

        public Builder withShipOption(String shipOption) {
            this.shipOption = shipOption;
            return this;
        }

        public Builder withOrderDate(ZonedDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }
        //CHECKSTYLE:ON:HiddenField
        //CHECKSTYLE:ON:JavadocMethod

        /**
         * Builds the OrderData from provided fields.
         *
         * @return populated OrderData object
         */
        public OrderData build() {
            OrderData data = new OrderData();

            data.orderId = orderId;
            data.customerId = customerId;
            data.marketplaceId = marketplaceId;
            data.condition = condition;
            data.customerOrderItemList = customerOrderItemList;
            data.customerShipments = customerShipments;
            data.shipOption = shipOption;
            data.orderDate = orderDate;

            return data;
        }
    }
}
