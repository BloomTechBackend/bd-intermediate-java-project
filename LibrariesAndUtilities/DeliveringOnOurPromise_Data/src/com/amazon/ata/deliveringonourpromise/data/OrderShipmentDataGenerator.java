package com.amazon.ata.deliveringonourpromise.data;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Responsible for generating valid OrderShipmentData.Builder objects for use by the OrderDatastore and/or
 * OrderDataGenerator.
 */
final class OrderShipmentDataGenerator {
    private static final String DEFAULT_ZIP = "98109";
    private static final String DEFAULT_SHIPMENT_CONDITION = "6";
    private static final String DEFAULT_SHIP_OPTION = "second";

    // singleton instance
    private static final OrderShipmentDataGenerator ORDER_SHIPMENT_GENERATOR_INSTANCE =
        new OrderShipmentDataGenerator();

    // valid warehouses to select from
    private static final List<String> WAREHOUSES = Arrays.asList("BFI4", "BFI7", "SEA8");

    // data that increment as we generate more data
    private long currentShipmentId = 10350858571111L;

    private ZonedDateTime currentShipmentCreationDate = OrderDataGenerator.INITIAL_ORDER_DATE.plusMinutes(23);
    private ZonedDateTime currentShipmentShipDate = OrderDataGenerator.INITIAL_ORDER_DATE.plusHours(7);
    private ZonedDateTime currentShipmentDeliveryDate = OrderDataGenerator.INITIAL_ORDER_DATE.plusDays(2);

    // counters for rotating through lists of valid values
    private int currentWarehouseIndex = 0;

    private OrderShipmentDataGenerator() {
    }

    /**
     * Returns a generator, ready to create OrderShipmentData.Builders
     *
     * @return OrderShipmentDataGenerator
     */
    static OrderShipmentDataGenerator getGenerator() {
        return ORDER_SHIPMENT_GENERATOR_INSTANCE;
    }

    /**
     * Returns an OrderShipmentData builder with reasonable default values, but does NOT
     * populate the shipment items. That must be done by caller.
     */
    OrderShipmentData.Builder buildShipmentData() {
        return OrderShipmentData.builder()
                   .withShipmentId(createShipmentId())
                   .withZip(createShipmentZip())
                   .withCondition(createShipmentCondition())
                   .withWarehouseId(createWarehouseId())
                   .withShipDate(createShipmentShipDate())
                   .withCreationDate(createShipmentCreationDate())
                   .withShipmentShipOption(createShipmentShipOption())
                   .withDeliveryDate(createShipmentDeliveryDate())
                   .withDoDpsAndOfsPromisesAgree(doDpsAndOfsPromisesAgree())
                   .withOnlyDpsPromisePresentAndActive(false);
    }

    /**
     * Creates a suitable CustomerShipmentItemData from a given customer OrderItemData.
     *
     * @param orderItemData The OrderItemData to bae the shipment item from
     * @return a constructed OrderShipmentData.CustomerShipmentItemData, suitable to be added to an
     * OrderShipmentData
     */
    OrderShipmentData.CustomerShipmentItemData createShipmentItemDataFromOrderItemData(
        OrderItemData orderItemData
    ) {
        return new OrderShipmentData.CustomerShipmentItemData(
            orderItemData.getCustomerOrderItemId(),
            orderItemData.getQuantity());
    }

    // helpers to deterministically return varying SHIPMENT default/sample data

    private String createShipmentId() {
        currentShipmentId += 11;
        return String.valueOf(currentShipmentId);
    }

    private String createShipmentZip() {
        return DEFAULT_ZIP;
    }

    private String createShipmentCondition() {
        return DEFAULT_SHIPMENT_CONDITION;
    }

    private String createWarehouseId() {
        currentWarehouseIndex = (currentWarehouseIndex + 1) % WAREHOUSES.size();
        return WAREHOUSES.get(currentWarehouseIndex % WAREHOUSES.size());
    }

    private ZonedDateTime createShipmentCreationDate() {
        currentShipmentCreationDate = incrementDate(currentShipmentCreationDate);
        return currentShipmentCreationDate;
    }

    private ZonedDateTime createShipmentShipDate() {
        currentShipmentShipDate = incrementDate(currentShipmentShipDate);
        return currentShipmentShipDate;
    }

    private String createShipmentShipOption() {
        return DEFAULT_SHIP_OPTION;
    }

    private ZonedDateTime createShipmentDeliveryDate() {
        currentShipmentDeliveryDate = incrementDate(currentShipmentDeliveryDate);
        return currentShipmentDeliveryDate;
    }

    private boolean doDpsAndOfsPromisesAgree() {
        return currentShipmentId % 3 == 0;
    }

    private ZonedDateTime incrementDate(ZonedDateTime dateTime) {
        return dateTime.plusHours(OrderDataGenerator.ORDER_DATA_DATE_HOURS_INCREMENT);
    }
}
