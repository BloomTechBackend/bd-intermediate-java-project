package com.amazon.ata.ordermanipulationauthority;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderManipulationAuthority's representation of a shipment associated with a customer order.
 */
public final class OrderShipment {
    private String shipmentId;
    private String zip;
    private String condition;
    private String warehouseId;
    private List<ShipmentItem> customerShipmentItems;
    private ZonedDateTime shipDate;
    private ZonedDateTime creationDate;
    private String shipmentShipOption;
    private ZonedDateTime deliveryDate;

    private OrderShipment() {}

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

    public List<ShipmentItem> getCustomerShipmentItems() {
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

    /**
     * Indicates if this shipment contains the specified order item.
     * @param orderItemId the order item ID to determine if this shipment includes or not
     * @return true if this shipment includes orderItemId; false otherwise
     */
    public boolean includesOrderItem(String orderItemId) {
        for (ShipmentItem shipmentItem : getCustomerShipmentItems()) {
            if (shipmentItem.getCustomerOrderItemId().equals(orderItemId)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public String toString() {
        return "OrderShipment{" +
               "shipmentId='" + shipmentId + '\'' +
               ", zip='" + zip + '\'' +
               ", condition='" + condition + '\'' +
               ", warehouseId='" + warehouseId + '\'' +
               ", customerShipmentItems=" + customerShipmentItems +
               ", shipDate=" + shipDate +
               ", creationDate=" + creationDate +
               ", shipmentShipOption='" + shipmentShipOption + '\'' +
               ", deliveryDate=" + deliveryDate +
               '}';
    }

    /**
     * Returns a builder object suitable for building an OrderShipment.
     * @return Builder, ready to build an OrderShipment
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder object suitable for building an OrderShipment.
     */
    public static class Builder {
        private String shipmentId;
        private String zip;
        private String condition;
        private String warehouseId;
        private List<ShipmentItem> customerShipmentItems;
        private ZonedDateTime shipDate;
        private ZonedDateTime creationDate;
        private String shipmentShipOption;
        private ZonedDateTime deliveryDate;

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

        public Builder withCustomerShipmentItems(List<ShipmentItem> customerShipmentItems) {
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
        //CHECKSTYLE:ON:HiddenField
        //CHECKSTYLE:ON:JavadocMethod

        /**
         * Builds and returns an OrderShipment from the builder state.
         * @return newly minted OrderShipment
         */
        public OrderShipment build() {
            OrderShipment orderShipment = new OrderShipment();
            orderShipment.shipmentId = shipmentId;
            orderShipment.zip = zip;
            orderShipment.condition = condition;
            orderShipment.warehouseId = warehouseId;
            orderShipment.customerShipmentItems = customerShipmentItems;
            orderShipment.shipDate = shipDate;
            orderShipment.creationDate = creationDate;
            orderShipment.shipmentShipOption = shipmentShipOption;
            orderShipment.deliveryDate = deliveryDate;
            return orderShipment;
        }
    }

    /**
     * Represents an order item in a given order shipment, with a reference to the order item that
     * is associated with the shipment.
     */
    public static class ShipmentItem {
        private String customerOrderItemId;
        private int quantity;

        /**
         * Creates a ShipmentItem from quantity and order item ID.
         * @param customerOrderItemId the order item that this shipment item corresponds to
         * @param quantity number of the item in this shipment
         */
        public ShipmentItem(String customerOrderItemId, int quantity) {
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
            return "ShipmentItem{" +
                   "customerOrderItemId='" + customerOrderItemId + '\'' +
                   ", quantity=" + quantity +
                   '}';
        }
    }
}
