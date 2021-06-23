package com.amazon.ata.deliveringonourpromise.data;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Responsible for generating valid OrderData.Builder objects for use by the OrderDatastore.
 */
final class OrderDataGenerator {
    // shared with other generators so they can keep dates ~in sync with order dates
    static final int ORDER_DATA_DATE_HOURS_INCREMENT = 9;
    static final ZonedDateTime INITIAL_ORDER_DATE = ZonedDateTime.of(
        2018, 7, 13, 15, 4, 11, 0, ZoneId.ofOffset("UTC", ZoneOffset.UTC)
    );

    // default values
    private static final String DEFAULT_MARKETPLACE_ID = "1 - US";
    // "CLOSED" magic number: 4 ==> OrderCondition.CLOSED
    private static final int DEFAULT_CONDITION = 4;
    private static final String DEFAULT_SHIP_OPTION = "second";

    // singleton instance
    private static final OrderDataGenerator ORDER_GENERATOR_INSTANCE = new OrderDataGenerator();

    // data that increment over time as we generate more data
    private long currentCustomerId = 375944378L;
    private ZonedDateTime currentOrderDate = INITIAL_ORDER_DATE;

    // generators for creating builders of contained data types (OrderShipmentData, OrderItemData)
    private final OrderShipmentDataGenerator orderShipmentDataGenerator = OrderShipmentDataGenerator.getGenerator();
    private final OrderItemDataGenerator orderItemDataGenerator = OrderItemDataGenerator.getGenerator();

    private OrderDataGenerator() {}

    /**
     * Returns a generator instance ready to create OrderData.Builders.
     * @return OrderDataGenerator
     */
    static OrderDataGenerator getGenerator() {
        return ORDER_GENERATOR_INSTANCE;
    }

    /**
     * Returns an Order builder with reasonable default values and caller can override if desired.
     * If caller would like to set items (and shipments) directly, call with numOrderItems = 0
     * @param numOrderItems The number of order items to create within the order (0 is allowed)
     * @return an OrderItem.Builder with valid values and the specified number of OrderItems
     */
    OrderData.Builder buildOrderData(int numOrderItems) {
        List<OrderItemData> orderItems = new ArrayList<>();
        List<OrderShipmentData> orderShipments = new ArrayList<>();

        // create order items
        for (int i = 0; i < numOrderItems; i++) {
            orderItems.add(orderItemDataGenerator.buildOrderItemData().build());
        }

        Iterator<OrderItemData> orderItemDataIterator = orderItems.iterator();

        // pick which order items get lumped into same shipments
        for (int itemsInShipment : Arrays.asList(1, 2, 1, 4, 1000)) {
            if (!orderItemDataIterator.hasNext()) {
                break;
            }

            List<OrderShipmentData.CustomerShipmentItemData> shipmentItems = new ArrayList<>();

            for (int i = 0; i < itemsInShipment; i++) {
                if (!orderItemDataIterator.hasNext()) {
                    break;
                }

                OrderItemData orderItemData = orderItemDataIterator.next();
                shipmentItems.add(orderShipmentDataGenerator.createShipmentItemDataFromOrderItemData(orderItemData));
            }

            // if there are any items in this shipment, add the shipment to the list
            if (!shipmentItems.isEmpty()) {
                orderShipments.add(orderShipmentDataGenerator.buildShipmentData()
                                       .withCustomerShipmentItems(shipmentItems)
                                       .build()
                );
            }
        }

        return OrderData.builder()
                   .withCondition(createOrderCondition())
                   .withCustomerId(createOrderCustomerId())
                   .withMarketplaceId(createOrderMarketplaceId())
                   .withOrderDate(createOrderDate())
                   .withShipOption(createOrderShipOption())
                   .withCustomerOrderItemList(orderItems)
                   .withCustomerShipments(orderShipments);
    }

    // helpers to deterministically return varying ORDER default/sample data

    private String createOrderCustomerId() {
        currentCustomerId += 7;
        return String.valueOf(currentCustomerId);
    }

    private int createOrderCondition() {
        return DEFAULT_CONDITION;
    }

    private String createOrderMarketplaceId() {
        return DEFAULT_MARKETPLACE_ID;
    }

    private ZonedDateTime createOrderDate() {
        currentOrderDate = incrementOrderDataDate(currentOrderDate);
        return currentOrderDate;
    }

    private String createOrderShipOption() {
        return DEFAULT_SHIP_OPTION;
    }

    // consistently bumps dates by same amount to keep them in consistent order with respect to one another
    private ZonedDateTime incrementOrderDataDate(ZonedDateTime time) {
        return time.plusHours(ORDER_DATA_DATE_HOURS_INCREMENT);
    }
}
