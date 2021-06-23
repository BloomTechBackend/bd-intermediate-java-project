package com.amazon.ata.deliveringonourpromise.data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data object from shared data provider that represents a shipment of items to a customer. An OrderData
 * will have one to many OrderShipmentData objects.
 */
public final class OrderShipmentData {
    private String shipmentId;
    private String zip;
    private String condition;
    private String warehouseId;
    private List<CustomerShipmentItemData> customerShipmentItems;
    private ZonedDateTime shipDate;
    private ZonedDateTime creationDate;
    private String shipmentShipOption;
    private ZonedDateTime deliveryDate;
    private boolean isDpsPromiseActive;
    private boolean isOfsPromiseActive;
    private boolean doDpsAndOfsPromisesAgree;

    private OrderShipmentData() {}

    public String getShipmentId() {
        return shipmentId;
    }

    public String getZip() {
        return zip;
    }

    public String getCondition() {
        return condition;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public List<CustomerShipmentItemData> getCustomerShipmentItems() {
        return new ArrayList<>(customerShipmentItems);
    }

    public ZonedDateTime getShipDate() {
        return shipDate;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public String getShipmentShipOption() {
        return shipmentShipOption;
    }

    public ZonedDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public boolean isDpsPromiseActive() {
        return isDpsPromiseActive;
    }

    public boolean isOfsPromiseActive() {
        return isOfsPromiseActive;
    }

    /**
     * Indicates if the DPS and OFS promises should be in agreement (true) for this shipment or not (false).
     * @return true if DPS and OFS should agree on promise; false otherwise
     */
    public boolean doDpsAndOfsPromisesAgree() {
        return doDpsAndOfsPromisesAgree;
    }

    /**
     * Indicates if this shipment contains the specified order item.
     * @param orderItemId the order item ID to determine if this shipment includes or not
     * @return true if this shipment includes orderItemId; false otherwise
     */
    public boolean includesOrderItem(String orderItemId) {
        for (CustomerShipmentItemData shipmentItem : getCustomerShipmentItems()) {
            if (shipmentItem.getCustomerOrderItemId().equals(orderItemId)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "OrderShipmentData{"
               + "shipmentId='" + shipmentId + '\''
               + ", zip='" + zip + '\''
               + ", condition='" + condition + '\''
               + ", warehouseId='" + warehouseId + '\''
               + ", customerShipmentItems=" + customerShipmentItems
               + ", shipDate=" + shipDate
               + ", creationDate=" + creationDate
               + ", shipmentShipOption='" + shipmentShipOption + '\''
               + ", deliveryDate=" + deliveryDate
               + ", isDpsPromiseActive=" + isDpsPromiseActive
               + ", isOfsPromiseActive=" + isOfsPromiseActive
               + ", doDpsAndOfsPromisesAgree" + doDpsAndOfsPromisesAgree
               + '}';
    }

    /**
     * Returns a builder object suitable for building an OrderShipmentData.
     * @return Builder, ready to build an OrderShipmentData
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder object suitable for building an OrderShipmentData.
     */
    public static class Builder {
        private String shipmentId;
        private String zip;
        private String condition;
        private String warehouseId;
        private List<CustomerShipmentItemData> customerShipmentItems;
        private ZonedDateTime shipDate;
        private ZonedDateTime creationDate;
        private String shipmentShipOption;
        private ZonedDateTime deliveryDate;
        private boolean isDpsPromiseActive;
        private boolean doDpsAndOfsPromisesAgree;

        //CHECKSTYLE:OFF:JavadocMethod
        //CHECKSTYLE:OFF:HiddenField
        public Builder withShipmentId(String shipmentId) {
            this.shipmentId = shipmentId;
            return this;
        }

        public Builder withZip(String zip) {
            this.zip = zip;
            return this;
        }

        public Builder withCondition(String condition) {
            this.condition = condition;
            return this;
        }

        public Builder withWarehouseId(String warehouseId) {
            this.warehouseId = warehouseId;
            return this;
        }

        public Builder withCustomerShipmentItems(List<CustomerShipmentItemData> customerShipmentItems) {
            this.customerShipmentItems = new ArrayList<>(customerShipmentItems);
            return this;
        }

        public Builder withShipDate(ZonedDateTime shipDate) {
            this.shipDate = shipDate;
            return this;
        }

        public Builder withCreationDate(ZonedDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder withShipmentShipOption(String shipmentShipOption) {
            this.shipmentShipOption = shipmentShipOption;
            return this;
        }

        public Builder withDeliveryDate(ZonedDateTime deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;
        }

        public Builder withDoDpsAndOfsPromisesAgree(boolean doDpsAndOfsPromisesAgree) {
            this.doDpsAndOfsPromisesAgree = doDpsAndOfsPromisesAgree;
            return this;
        }

        /**
         * Set whether the DPS promise is current active promise (true) or not (false).
         *
         * @param isOnlyDpsPromisePresentAndActive True if DPS should be active (and no OFS present); false if
         *                                         both DPS and OFS promises are present and OFS promise is active.
         * @return updated Builder
         */
        public Builder withOnlyDpsPromisePresentAndActive(boolean isOnlyDpsPromisePresentAndActive) {
            this.isDpsPromiseActive = isOnlyDpsPromisePresentAndActive;
            return this;
        }
        //CHECKSTYLE:ON:HiddenField
        //CHECKSTYLE:ON:JavadocMethod

        /**
         * Builds and returns an OrderShipmentData from the builder state.
         * @return newly minted OrderShipmentData
         */
        public OrderShipmentData build() {
            OrderShipmentData orderShipmentData = new OrderShipmentData();
            orderShipmentData.shipmentId = shipmentId;
            orderShipmentData.zip = zip;
            orderShipmentData.condition = condition;
            orderShipmentData.warehouseId = warehouseId;
            orderShipmentData.customerShipmentItems = customerShipmentItems;
            orderShipmentData.shipDate = shipDate;
            orderShipmentData.creationDate = creationDate;
            orderShipmentData.shipmentShipOption = shipmentShipOption;
            orderShipmentData.deliveryDate = deliveryDate;
            orderShipmentData.isDpsPromiseActive = isDpsPromiseActive;
            orderShipmentData.isOfsPromiseActive = !isDpsPromiseActive;
            orderShipmentData.doDpsAndOfsPromisesAgree = doDpsAndOfsPromisesAgree;
            return orderShipmentData;
        }
    }

    /**
     * Represents an order item in a given order shipment, with a reference to the order item that
     * is associated with the shipment.
     */
    public static class CustomerShipmentItemData {
        private String customerOrderItemId;
        private int quantity;

        /**
         * Creates a CustomerShipmentItemData from quantity and order item ID.
         * @param customerOrderItemId the order item that this shipment item corresponds to
         * @param quantity number of the item in this shipment
         */
        public CustomerShipmentItemData(String customerOrderItemId, int quantity) {
            this.customerOrderItemId = customerOrderItemId;
            this.quantity = quantity;
        }

        public String getCustomerOrderItemId() {
            return customerOrderItemId;
        }

        public int getQuantity() {
            return quantity;
        }

        @Override
        public String toString() {
            return "CustomerShipmentItemData{"
                   + "customerOrderItemId='" + customerOrderItemId + '\''
                   + ", quantity=" + quantity
                   + '}';
        }
    }
}
