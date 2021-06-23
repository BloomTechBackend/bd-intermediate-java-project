package com.amazon.ata.deliveringonourpromise.data;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generates the specific OrderFixtures that we want available from the OrderDatastore.
 */
public final class OrderFixtureGenerator {
    // what time to base all orders around, so that we always return same data for same records
    private static final ZonedDateTime DATE_TIME_ORIGIN = ZonedDateTime.of(
        2019, 6, 3, 9, 43, 18, 0, ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-8))
    );
    private static final int APPROVED_ORDER_CONDITION = 3;

    private static final OrderFixtureGenerator ORDER_FIXTURE_GENERATOR_INSTANCE = new OrderFixtureGenerator();

    private final OrderDataGenerator orderDataGenerator = OrderDataGenerator.getGenerator();
    private final OrderItemDataGenerator orderItemDataGenerator = OrderItemDataGenerator.getGenerator();
    private final OrderShipmentDataGenerator orderShipmentDataGenerator = OrderShipmentDataGenerator.getGenerator();

    private OrderFixtureGenerator() {
    }

    /**
     * Returns OrderFixtureGenerator instance.
     *
     * @return an OrderFixtureGenerator
     */
    public static OrderFixtureGenerator getGenerator() {
        return ORDER_FIXTURE_GENERATOR_INSTANCE;
    }

    public Map<String, OrderFixture> generateOrderFixtures() {
        Map<String, OrderFixture> orderFixtures = new HashMap<>();
        OrderData orderData;

        // one item per order

        orderData = singleItemNotShipped("900-3746401-0000001").build();
        orderFixtures.put(
            orderData.getOrderId(),
            new OrderFixture(orderData,
                             "single item, not shipped"));

        orderData = singleItemShippedNotDelivered("900-3746401-0000002").build();
        orderFixtures.put(
            orderData.getOrderId(),
            new OrderFixture(orderData,
                             "single item, shipped, not delivered"));

        orderData = singleItemShippedAndDelivered("900-3746401-0000003").build();
        orderFixtures.put(
            orderData.getOrderId(),
            new OrderFixture(orderData,
                             "single item, shipped and delivered"));

        // two items per order

        orderData = twoItemsTwoShipmentsOneNotShippedOneDelivered("900-3746402-0000001").build();
        orderFixtures.put(
            orderData.getOrderId(),
            new OrderFixture(orderData,
                             "2 items/shipments, 1 unshipped, 1 delivered"));

        orderData = twoItemsOneShipmentsNotShippedDpsOfsPromisesDisagree("900-3746402-0000002").build();
        orderFixtures.put(
            orderData.getOrderId(),
            new OrderFixture(orderData,
                             "2 items, 1 shipment promises disagree, not shipped"));

        // three items per order

        orderData = threeItemsTwoShipmentsOneShippedDpsOfsDisagreeOneDelivered("900-3746403-0000001").build();
        orderFixtures.put(
            orderData.getOrderId(),
            new OrderFixture(orderData,
                             "3 items, 2 shipments, 1 shipped promises disagree, 1 delivered"));

        orderData = threeItemsThreeShipmentsTwoNotShippedOneDelivered("900-3746403-0000002").build();
        orderFixtures.put(
            orderData.getOrderId(),
            new OrderFixture(orderData,
                             "3 items/shipments, 2 unshipped, 1 delivered promises disagree"));

        // valid but missing order ID
        orderFixtures.put(
            "900-0000000-0000000",
            new OrderFixture(null, "valid order ID but non-existing order")
        );

        // ADD MORE CASES HERE AS NEEDED FOR IRTs, TCTs etc.

        return orderFixtures;
    }

    // CASES: one item per order

    private OrderData.Builder singleItemNotShipped(String orderId) {
        ZonedDateTime orderDate = DATE_TIME_ORIGIN.minusHours(4).plusMinutes(33).plusSeconds(1);
        OrderItemData orderItem = orderItemDataGenerator.buildOrderItemData()
                                      .withOrderId(orderId)
                                      .build();
        OrderShipmentData orderShipment =
            orderShipmentDataGenerator.buildShipmentData()
                .withCustomerShipmentItems(
                    Arrays.asList(orderShipmentDataGenerator.createShipmentItemDataFromOrderItemData(orderItem)))
                .withCreationDate(orderDate.plusMinutes(20))
                .withShipDate(null)
                .withDeliveryDate(null)
                .withOnlyDpsPromisePresentAndActive(true)
                .build();

        return orderDataGenerator.buildOrderData(0)
                   .withOrderId(orderId)
                   .withOrderDate(orderDate)
                   .withCustomerOrderItemList(Arrays.asList(orderItem))
                   .withCustomerShipments(Arrays.asList(orderShipment))
                   .withCondition(APPROVED_ORDER_CONDITION);
    }

    private OrderData.Builder singleItemShippedNotDelivered(String orderId) {
        ZonedDateTime orderDate = DATE_TIME_ORIGIN.minusHours(8).minusMinutes(6).minusSeconds(22);
        OrderItemData orderItem = orderItemDataGenerator.buildOrderItemData()
                                      .withOrderId(orderId)
                                      .build();
        OrderShipmentData orderShipment =
            orderShipmentDataGenerator.buildShipmentData()
                .withCustomerShipmentItems(
                    Arrays.asList(orderShipmentDataGenerator.createShipmentItemDataFromOrderItemData(orderItem)))
                .withCreationDate(orderDate.plusMinutes(20))
                .withShipDate(orderDate.plusHours(1))
                .withDeliveryDate(null)
                .withDoDpsAndOfsPromisesAgree(true)
                .withOnlyDpsPromisePresentAndActive(false)
                .build();

        return orderDataGenerator.buildOrderData(0)
                   .withOrderId(orderId)
                   .withOrderDate(orderDate)
                   .withCustomerOrderItemList(Arrays.asList(orderItem))
                   .withCustomerShipments(Arrays.asList(orderShipment))
                   .withCondition(APPROVED_ORDER_CONDITION);
    }

    private OrderData.Builder singleItemShippedAndDelivered(String orderId) {
        ZonedDateTime orderDate = DATE_TIME_ORIGIN.minusHours(8).plusMinutes(13).minusSeconds(9);
        OrderItemData orderItem = orderItemDataGenerator.buildOrderItemData()
                                      .withOrderId(orderId)
                                      .build();
        OrderShipmentData orderShipment =
            orderShipmentDataGenerator.buildShipmentData()
                .withCustomerShipmentItems(
                    Arrays.asList(orderShipmentDataGenerator.createShipmentItemDataFromOrderItemData(orderItem)))
                .withCreationDate(orderDate.plusMinutes(20))
                .withShipDate(orderDate.plusHours(1))
                .withDeliveryDate(orderDate.plusHours(36))
                .withOnlyDpsPromisePresentAndActive(false)
                .withDoDpsAndOfsPromisesAgree(true)
                .build();

        return orderDataGenerator.buildOrderData(0)
                   .withOrderId(orderId)
                   .withOrderDate(orderDate)
                   .withCustomerOrderItemList(Arrays.asList(orderItem))
                   .withCustomerShipments(Arrays.asList(orderShipment))
                   .withCondition(APPROVED_ORDER_CONDITION);
    }

    // CASES: two items per order

    private OrderData.Builder twoItemsTwoShipmentsOneNotShippedOneDelivered(String orderId) {
        ZonedDateTime orderDate = DATE_TIME_ORIGIN.minusHours(8);
        OrderItemData unshippedOrderItemData =
            orderItemDataGenerator.buildOrderItemData().withOrderId(orderId).build();
        OrderItemData deliveredOrderItemData =
            orderItemDataGenerator.buildOrderItemData().withOrderId(orderId).build();

        OrderShipmentData.CustomerShipmentItemData unshippedShipmentItemData =
            orderShipmentDataGenerator.createShipmentItemDataFromOrderItemData(unshippedOrderItemData);
        OrderShipmentData.CustomerShipmentItemData deliveredShipmentItemData =
            orderShipmentDataGenerator.createShipmentItemDataFromOrderItemData(deliveredOrderItemData);

        OrderShipmentData unshippedShipmentData =
            orderShipmentDataGenerator.buildShipmentData()
                .withCustomerShipmentItems(Arrays.asList(unshippedShipmentItemData))
                .withCreationDate(orderDate.plusHours(2))
                .withShipDate(null)
                .withDeliveryDate(null)
                .withDoDpsAndOfsPromisesAgree(true)
                .withOnlyDpsPromisePresentAndActive(true)
                .build();

        OrderShipmentData deliveredShipmentData =
            orderShipmentDataGenerator.buildShipmentData()
                .withCustomerShipmentItems(Arrays.asList(deliveredShipmentItemData))
                .withCreationDate(orderDate.plusMinutes(45))
                .withShipDate(orderDate.plusHours(6))
                .withDeliveryDate(orderDate.plusHours(72))
                .withOnlyDpsPromisePresentAndActive(false)
                .build();

        return orderDataGenerator.buildOrderData(0)
                   .withOrderId(orderId)
                   .withOrderDate(orderDate)
                   .withCustomerOrderItemList(Arrays.asList(unshippedOrderItemData, deliveredOrderItemData))
                   .withCustomerShipments(Arrays.asList(unshippedShipmentData, deliveredShipmentData))
                   .withCondition(APPROVED_ORDER_CONDITION);
    }

    private OrderData.Builder twoItemsOneShipmentsNotShippedDpsOfsPromisesDisagree(String orderId) {
        ZonedDateTime orderDate = DATE_TIME_ORIGIN.minusHours(6).plusMinutes(37);
        List<OrderItemData> orderItems = Arrays.asList(
            orderItemDataGenerator.buildOrderItemData().withOrderId(orderId).build(),
            orderItemDataGenerator.buildOrderItemData().withOrderId(orderId).build()
        );

        List<OrderShipmentData.CustomerShipmentItemData> shipmentItems =
            orderItems.stream()
                .map(orderShipmentDataGenerator::createShipmentItemDataFromOrderItemData)
                .collect(Collectors.toList());

        OrderShipmentData shipmentData =
            orderShipmentDataGenerator.buildShipmentData()
                .withCustomerShipmentItems(shipmentItems)
                .withCreationDate(orderDate.plusHours(3).plusSeconds(11))
                .withShipDate(null)
                .withDeliveryDate(null)
                .withDoDpsAndOfsPromisesAgree(false)
                .withOnlyDpsPromisePresentAndActive(false)
                .build();

        return orderDataGenerator.buildOrderData(0)
                   .withOrderId(orderId)
                   .withOrderDate(orderDate)
                   .withCustomerOrderItemList(orderItems)
                   .withCustomerShipments(Arrays.asList(shipmentData))
                   .withCondition(APPROVED_ORDER_CONDITION);
    }

    private OrderData.Builder threeItemsTwoShipmentsOneShippedDpsOfsDisagreeOneDelivered(String orderId) {
        ZonedDateTime orderDate = DATE_TIME_ORIGIN.minusHours(46).minusMinutes(12).plusSeconds(18);

        List<OrderItemData> shippedOrderItems = Arrays.asList(
            orderItemDataGenerator.buildOrderItemData().withOrderId(orderId).build(),
            orderItemDataGenerator.buildOrderItemData().withOrderId(orderId).build()
        );
        OrderItemData deliveredOrderItem = orderItemDataGenerator.buildOrderItemData().withOrderId(orderId).build();
        List<OrderItemData> orderItems = new ArrayList<>(shippedOrderItems);
        orderItems.add(deliveredOrderItem);

        List<OrderShipmentData.CustomerShipmentItemData> shippedShipmentItems =
            shippedOrderItems.stream()
                .map(orderShipmentDataGenerator::createShipmentItemDataFromOrderItemData)
                .collect(Collectors.toList());
        OrderShipmentData.CustomerShipmentItemData deliveredShipmentItem =
            orderShipmentDataGenerator.createShipmentItemDataFromOrderItemData(deliveredOrderItem);

        OrderShipmentData shippedShipmentData =
            orderShipmentDataGenerator.buildShipmentData()
                .withCustomerShipmentItems(shippedShipmentItems)
                .withCreationDate(orderDate.plusHours(8).plusMinutes(1).plusSeconds(29))
                .withShipDate(orderDate.plusHours(11).plusMinutes(10))
                .withDeliveryDate(null)
                .withDoDpsAndOfsPromisesAgree(false)
                .withOnlyDpsPromisePresentAndActive(false)
                .build();

        OrderShipmentData deliveredShipmentData =
            orderShipmentDataGenerator.buildShipmentData()
                .withCustomerShipmentItems(Arrays.asList(deliveredShipmentItem))
                .withCreationDate(orderDate.plusHours(2))
                .withShipDate(orderDate.plusHours(6).plusMinutes(27))
                .withDeliveryDate(orderDate.plusHours(47))
                .withDoDpsAndOfsPromisesAgree(true)
                .withOnlyDpsPromisePresentAndActive(false)
                .build();

        return orderDataGenerator.buildOrderData(0)
                   .withOrderId(orderId)
                   .withOrderDate(orderDate)
                   .withCustomerOrderItemList(orderItems)
                   .withCustomerShipments(Arrays.asList(shippedShipmentData, deliveredShipmentData))
                   .withCondition(APPROVED_ORDER_CONDITION);
    }

    private OrderData.Builder threeItemsThreeShipmentsTwoNotShippedOneDelivered(String orderId) {
        ZonedDateTime orderDate = DATE_TIME_ORIGIN.minusHours(71).minusMinutes(44).plusSeconds(8);

        OrderItemData orderItemUnshippedShipment1 =
            orderItemDataGenerator.buildOrderItemData().withOrderId(orderId).build();
        OrderItemData orderItemUnshippedShipment2 =
            orderItemDataGenerator.buildOrderItemData().withOrderId(orderId).build();
        OrderItemData orderItemDeliveredShipment3 =
            orderItemDataGenerator.buildOrderItemData().withOrderId(orderId).build();
        List<OrderItemData> orderItems = Arrays.asList(orderItemUnshippedShipment1,
                                                       orderItemUnshippedShipment2,
                                                       orderItemDeliveredShipment3);

        OrderShipmentData.CustomerShipmentItemData orderUnshippedShipmentItem1 =
            orderShipmentDataGenerator.createShipmentItemDataFromOrderItemData(orderItemUnshippedShipment1);
        OrderShipmentData.CustomerShipmentItemData orderUnshippedShipmentItem2 =
            orderShipmentDataGenerator.createShipmentItemDataFromOrderItemData(orderItemUnshippedShipment2);
        OrderShipmentData.CustomerShipmentItemData orderDeliveredShipmentItem3 =
            orderShipmentDataGenerator.createShipmentItemDataFromOrderItemData(orderItemDeliveredShipment3);

        OrderShipmentData unshippedShipment1 =
            orderShipmentDataGenerator.buildShipmentData()
                .withCustomerShipmentItems(Arrays.asList(orderUnshippedShipmentItem1))
                .withCreationDate(orderDate.plusHours(6).plusMinutes(5).plusSeconds(11))
                .withShipDate(null)
                .withDeliveryDate(null)
                .withDoDpsAndOfsPromisesAgree(true)
                .withOnlyDpsPromisePresentAndActive(true)
                .build();

        OrderShipmentData unshippedShipment2 =
            orderShipmentDataGenerator.buildShipmentData()
                .withCustomerShipmentItems(Arrays.asList(orderUnshippedShipmentItem2))
                .withCreationDate(orderDate.plusHours(2).plusSeconds(41))
                .withShipDate(null)
                .withDeliveryDate(null)
                .withDoDpsAndOfsPromisesAgree(true)
                .withOnlyDpsPromisePresentAndActive(true)
                .build();

        OrderShipmentData deliveredShipment3 =
            orderShipmentDataGenerator.buildShipmentData()
                .withCustomerShipmentItems(Arrays.asList(orderDeliveredShipmentItem3))
                .withCreationDate(orderDate.plusMinutes(17).plusSeconds(1))
                .withShipDate(orderDate.plusHours(2).plusMinutes(18))
                .withDeliveryDate(orderDate.plusHours(18).plusSeconds(10))
                .withDoDpsAndOfsPromisesAgree(false)
                .withOnlyDpsPromisePresentAndActive(false)
                .build();
        List<OrderShipmentData> orderShipments = Arrays.asList(unshippedShipment1,
                                                               unshippedShipment2,
                                                               deliveredShipment3);

        return orderDataGenerator.buildOrderData(0)
                   .withOrderId(orderId)
                   .withOrderDate(orderDate)
                   .withCustomerOrderItemList(orderItems)
                   .withCustomerShipments(orderShipments)
                   .withCondition(APPROVED_ORDER_CONDITION);
    }
}
