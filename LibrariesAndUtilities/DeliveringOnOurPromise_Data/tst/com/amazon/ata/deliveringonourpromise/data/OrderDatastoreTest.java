package com.amazon.ata.deliveringonourpromise.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class OrderDatastoreTest {
    private static final List<String> SINGLE_ITEM_ORDER_FIXTURES = Arrays.asList(
        "900-3746401-0000001"
        , "900-3746401-0000002"
        , "900-3746401-0000003"
    );

    private static final List<String> DOUBLE_ITEM_ORDER_FIXTURES = Arrays.asList(
        "900-3746402-0000001"
        , "900-3746402-0000002"
    );

    private static final List<String> TRIPLE_ITEM_ORDER_FIXTURES = Arrays.asList(
        "900-3746403-0000001"
        , "900-3746403-0000002"
    );

    private static final String NON_EXISTING_ORDER_ID = "900-0000000-0000000";

    private static List<String> allFixtureOrderIds;


    private OrderDatastore orderDatastore = OrderDatastore.getDatastore();

    @BeforeAll
    public static void setup() {
        allFixtureOrderIds = new ArrayList<>();
        allFixtureOrderIds.addAll(SINGLE_ITEM_ORDER_FIXTURES);
        allFixtureOrderIds.addAll(DOUBLE_ITEM_ORDER_FIXTURES);
        allFixtureOrderIds.addAll(TRIPLE_ITEM_ORDER_FIXTURES);
        allFixtureOrderIds.add(NON_EXISTING_ORDER_ID);
    }

    @Test
    public void getOrderData_severalConsecutiveOrdersHaveDifferentOrderItems() {
        // WHEN
        OrderData data1 = orderDatastore.getOrderData("111-7497023-2960775");
        OrderData data2 = orderDatastore.getOrderData("111-7497023-2960776");
        OrderData data3 = orderDatastore.getOrderData("111-7497023-2960777");

        // THEN - orders exist and items don't match
        assertNotNull(data1);
        assertNotNull(data2);
        assertNotNull(data3);
        assertNotEquals(data1.getCustomerOrderItemList(), data2.getCustomerOrderItemList());
        assertNotEquals(data1.getCustomerOrderItemList(), data3.getCustomerOrderItemList());
        assertNotEquals(data2.getCustomerOrderItemList(), data3.getCustomerOrderItemList());
    }

    @Test
    public void getOrderData_nonNumericOrderIdReturnsNull() {
        // WHEN
        OrderData nonNumericOrderIdData = orderDatastore.getOrderData("NOT ORDER ID");

        // THEN
        assertNull(nonNumericOrderIdData);
    }

    @Test
    public void getOrderData_invalidDigitFormatIdReturnsNull() {
        // WHEN
        OrderData wrongDigitsOrderId = orderDatastore.getOrderData("111-1-999999");

        // THEN
        assertNull(wrongDigitsOrderId);
    }

    @Test
    public void getOrderData_hasAtLeastOneOrderItem() {
        // GIVEN
        List<String> orderIds = generateOrderIds("111-7497023-2960777");

        // WHEN
        List<OrderData> orderDatas = orderIds.stream()
                                         .map(s -> orderDatastore.getOrderData(s))
                                         .collect(Collectors.toList());

        // THEN
        for (OrderData data : orderDatas) {
            assertTrue(data.getCustomerOrderItemList().size() > 0,
                       String.format("Expected order item list not to be empty for orderID %s", data.getOrderId())
            );
        }
    }

    @Test
    public void getOrderData_orderIdsMatch() {
        // GIVEN
        String orderId = "111-7497023-2960776";

        // WHEN
        OrderData data = orderDatastore.getOrderData(orderId);

        // THEN
        assertEquals(orderId, data.getOrderId());
        for (OrderItemData item : data.getCustomerOrderItemList()) {
            assertEquals(orderId,
                         item.getOrderId(),
                         String.format("Expected orderId '%s' to have order items that all point to the "
                                       + "same order ID, but order item '%s' has order Id '%s' instead",
                                       data.getOrderId(),
                                       item.getCustomerOrderItemId(),
                                       item.getOrderId()
                         )
            );
        }
    }

    @Test
    public void getOrderData_allAsinsAndTitlesExist() {
        // GIVEN
        List<String> orderIds = generateOrderIds("112-7497023-2960001");

        // WHEN
        List<OrderData> orderDatas = orderIds.stream()
                                         .map(s -> orderDatastore.getOrderData(s))
                                         .collect(Collectors.toList());

        // THEN
        for (OrderData data : orderDatas) {
            for (OrderItemData item : data.getCustomerOrderItemList()) {
                assertNotNull(item.getAsin(),
                              String.format("Expected order ID '%s' to contain order items with non-null ASINs, "
                                            + "but order item '%s' has null ASIN",
                                            data.getOrderId(),
                                            item.getCustomerOrderItemId()
                              )
                );
                assertNotNull(item.getTitle());
            }
        }
    }

    @Test
    public void getOrderData_allOrderItemsAccountedForInShipmentItems() {
        // GIVEN
        List<String> orderIds = generateOrderIds("112-7497023-2960010");

        // WHEN
        List<OrderData> orderDatas = orderIds.stream()
                                         .map(s -> orderDatastore.getOrderData(s))
                                         .collect(Collectors.toList());

        // THEN
        for (OrderData data : orderDatas) {
            // collect order items from order, initialize map values to false
            Map<String, Boolean> orderItemIdsFound =
                data.getCustomerOrderItemList().stream()
                    .collect(Collectors.toMap(OrderItemData::getCustomerOrderItemId, oi -> false));

            // for each shipment in order, record which order items it contains by setting map value to true
            for (OrderShipmentData orderShipmentData : data.getCustomerShipments()) {
                for (OrderShipmentData.CustomerShipmentItemData shipmentItem : orderShipmentData
                                                                                   .getCustomerShipmentItems()) {
                    if (orderItemIdsFound.containsKey(shipmentItem.getCustomerOrderItemId())) {
                        orderItemIdsFound.put(shipmentItem.getCustomerOrderItemId(), true);
                    }
                }
            }

            // see if any order items were not accounted for in shipments
            for (Map.Entry<String, Boolean> orderItemFound : orderItemIdsFound.entrySet()) {
                assertTrue(orderItemFound.getValue(),
                           String.format("Expected order '%s' to contain shipments that cover all "
                                         + "order items in order (order items: %s), but order item '%s' was not "
                                         + "found "
                                         + "in shipments: %s",
                                         data.getOrderId(),
                                         data.getCustomerOrderItemList().stream()
                                             .map(OrderItemData::getCustomerOrderItemId).collect(Collectors.toList()),
                                         orderItemFound.getKey(),
                                         data.getCustomerShipments().toString())
                );
            }
        }
    }

    @Test
    void getOrderData_shipmentItemsQuantitiesLessThanOrEqualToOrderItems() {
        // GIVEN
        List<String> orderIds = generateOrderIds("112-7497023-2960210");

        // WHEN
        List<OrderData> orderDatas = orderIds.stream()
                                         .map(s -> orderDatastore.getOrderData(s))
                                         .collect(Collectors.toList());

        // THEN
        for (OrderData data : orderDatas) {
            // collect order items and their quantities from order
            Map<String, Integer> orderItemQuantities =
                data.getCustomerOrderItemList().stream()
                    .collect(Collectors.toMap(OrderItemData::getCustomerOrderItemId,
                                              OrderItemData::getQuantity));

            // for each shipment in order, make sure that their shipment item quantities match
            for (OrderShipmentData orderShipmentData : data.getCustomerShipments()) {
                for (OrderShipmentData.CustomerShipmentItemData shipmentItem : orderShipmentData
                                                                                   .getCustomerShipmentItems()) {
                    assertEquals(orderItemQuantities.get(shipmentItem.getCustomerOrderItemId()).intValue(),
                                 shipmentItem.getQuantity(),
                                 String.format("Expected order '%s' to have shipment items with quantities "
                                               + "that match the order's order items, but order item '%s' "
                                               + "has quantity '%d' and shipment item '%s' with order item id '%s' "
                                               + "has quantity '%d'",
                                               data.getOrderId(),
                                               shipmentItem.getCustomerOrderItemId(),
                                               orderItemQuantities.get(shipmentItem.getCustomerOrderItemId()),
                                               orderShipmentData.getShipmentId(),
                                               shipmentItem.getCustomerOrderItemId(),
                                               shipmentItem.getQuantity()
                                 )
                    );
                }
            }
        }
    }

    @Test
    void getOrderData_shipmentCreationDateNotBeforeOrderDate() {
        // GIVEN
        List<String> orderIds = generateOrderIds("112-7497023-2960210");

        // WHEN
        List<OrderData> orderDatas = orderIds.stream()
                                         .map(s -> orderDatastore.getOrderData(s))
                                         .collect(Collectors.toList());

        // THEN
        for (OrderData data : orderDatas) {
            // for each shipment, ensure that shipment creation date is after order date
            for (OrderShipmentData orderShipmentData : data.getCustomerShipments()) {
                assertTrue(data.getOrderDate().compareTo(orderShipmentData.getCreationDate()) < 0,
                           String.format("Expected order '%s' to have order date before each of "
                                         + "its shipment creation dates, but the order's order date: "
                                         + "'%s' is after shipment '%s' creation date: '%s'",
                                         data.getOrderId(),
                                         orderShipmentData.getShipmentId(),
                                         data.getOrderDate().toString(),
                                         orderShipmentData.getCreationDate().toString()
                           )
                );
            }
        }
    }

    @Test
    void getOrderData_shipmentShipDateIsNullOrAfterOrderCreationDate() {
        // GIVEN
        List<String> orderIds = generateOrderIds("112-7497023-2960310");

        // WHEN
        List<OrderData> orderDatas = orderIds.stream()
                                         .map(s -> orderDatastore.getOrderData(s))
                                         .collect(Collectors.toList());

        // THEN
        for (OrderData data : orderDatas) {
            // for each shipment, ensure that creation date is before ship date (or ship date is null)
            for (OrderShipmentData orderShipmentData : data.getCustomerShipments()) {
                if (null == orderShipmentData.getShipDate()) {
                    continue;
                }

                assertTrue(orderShipmentData.getCreationDate().compareTo(orderShipmentData.getShipDate()) < 0,
                           String.format("Expected order '%s' to have shipments with creation date before "
                                         + "their ship dates, but shipment id '%s' has "
                                         + "creation date '%s' which is after ship date '%s'",
                                         data.getOrderId(),
                                         orderShipmentData.getShipmentId(),
                                         orderShipmentData.getCreationDate().toString(),
                                         orderShipmentData.getShipDate().toString()
                           )
                );
            }
        }
    }

    @Test
    void getOrderData_shipmentDeliveryDateIsNullOrAfterShipmentShipDate() {
        // GIVEN
        List<String> orderIds = generateOrderIds("112-7497023-2960310");

        // WHEN
        List<OrderData> orderDatas = orderIds.stream()
                                         .map(s -> orderDatastore.getOrderData(s))
                                         .collect(Collectors.toList());

        // THEN
        for (OrderData data : orderDatas) {
            // for each shipment, ensure that ship date is before delivery date (or delivery date is null)
            for (OrderShipmentData orderShipmentData : data.getCustomerShipments()) {
                // if null ship date, ensure that delivery date is null as well (can't deliver without being shipped!)
                if (null == orderShipmentData.getShipDate()) {
                    assertNull(orderShipmentData.getDeliveryDate(),
                               String.format("Expected order '%s' with a shipment '%s' with null ship date "
                                             + "to also have null delivery date, but had delivery date of %s",
                                             data.getOrderId(),
                                             orderShipmentData.getShipmentId(),
                                             orderShipmentData.getDeliveryDate()
                               )
                    );

                    continue;
                }

                if (null == orderShipmentData.getDeliveryDate()) {
                    continue;
                }

                assertTrue(orderShipmentData.getShipDate().compareTo(orderShipmentData.getDeliveryDate()) < 0,
                           String.format("Expected order '%s' to have shipments with ship date before "
                                         + "their delivery dates, but shipment id '%s' has "
                                         + "ship date '%s' which is after delivery date '%s'",
                                         data.getOrderId(),
                                         orderShipmentData.getShipmentId(),
                                         orderShipmentData.getShipDate(),
                                         orderShipmentData.getDeliveryDate()
                           )
                );
            }
        }
    }

    @Test
    void getOrderData_orderIsEitherNotClosedOrAllShipmentsHaveDeliveryDate() {
        // GIVEN
        List<String> orderIds = generateOrderIds("112-7497023-2960310");

        // WHEN
        List<OrderData> orderDatas = orderIds.stream()
                                         .map(s -> orderDatastore.getOrderData(s))
                                         .collect(Collectors.toList());

        // THEN
        for (OrderData data : orderDatas) {
            // magic number: 4 ==> OrderCondition.CLOSED
            if (data.getCondition() != 4) {
                continue;
            }

            // ensure every shipment has a delivery date
            for (OrderShipmentData orderShipmentData : data.getCustomerShipments()) {
                assertNotNull(orderShipmentData.getDeliveryDate(),
                              String.format("Expected CLOSED order '%s' to have shipments that all have "
                                            + "delivery dates, but shipment '%s' has null delivery date",
                                            data.getOrderId(),
                                            orderShipmentData.getShipmentId()
                              )
                );
            }
        }
    }

    @Test
    public void getOrderData_fixtureOrders_matchGetOrderFixturesMap() {
        // GIVEN - all fixture order IDs
        // WHEN + THEN
        for (String orderId : allFixtureOrderIds) {
            OrderFixture orderFixture = orderDatastore.getOrderFixtures().get(orderId);
            OrderData orderData = orderFixture.getOrderData();
            assertEquals(orderData, orderDatastore.getOrderData(orderId),
                         String.format("Expected order data store to return the same order for order fixture " +
                                       "with order ID '%s'. But order fixture was: %s and getOrderData returned: %s",
                                       orderId,
                                       orderFixture.toString(),
                                       orderDatastore.getOrderData(orderId)));
        }
    }

    @Test
    public void getOrderData_nonexistentOrderFixture_returnsNull() {
        // GIVEN - valid order ID but order doesn't exist

        // WHEN
        OrderData missingOrder = orderDatastore.getOrderData(NON_EXISTING_ORDER_ID);

        // THEN
        assertNull(missingOrder);
    }

    @Test
    public void getOrderItemData_agreesWithGetOrderData() {
        // GIVEN
        List<String> orderIds = generateOrderIds("112-7497023-1961239");

        // WHEN
        List<OrderData> orderDatas = orderIds.stream()
                                         .map(s -> orderDatastore.getOrderData(s))
                                         .collect(Collectors.toList());

        // THEN
        for (OrderData data : orderDatas) {
            for (OrderItemData itemData : data.getCustomerOrderItemList()) {
                OrderItemData fetchedItemData = orderDatastore.getOrderItemData(itemData.getCustomerOrderItemId());
                assertEquals(itemData,
                             fetchedItemData,
                             String.format("Expected order '%s' to have order item '%s' "
                                           + "that matches the order item returned by datastore but they do not. "
                                           + "order's order item: %s | getOrderItemData('%s') returns: %s",
                                           data.getOrderId(),
                                           itemData.getCustomerOrderItemId(),
                                           itemData.toString(),
                                           itemData.getCustomerOrderItemId(),
                                           fetchedItemData.toString()
                             )
                );
            }
        }
    }

    @Test
    public void getOrderFixtures_containsExpectedSingleItemOrders() {
        // GIVEN - singleItemOrderFixtures

        // WHEN
        Map<String, OrderFixture> fixtures = orderDatastore.getOrderFixtures();

        // THEN - each of the expected fixtures exists and has a single order item
        for (String orderId : SINGLE_ITEM_ORDER_FIXTURES) {
            OrderFixture orderFixture = fixtures.get(orderId);
            assertNotNull(orderFixture,
                          String.format("Expected order ID '%s' to exist in fixtures but it does not. Fixtures: %s",
                                        orderId,
                                        fixtures
                          )
            );
            assertEquals(1, orderFixture.getOrderData().getCustomerOrderItemList().size());
        }
    }

    @Test
    public void getOrderFixtures_containsExpectedDoubleItemOrders() {
        // GIVEN - DOUBLE_ITEM_ORDER_FIXTURES

        // WHEN
        Map<String, OrderFixture> fixtures = orderDatastore.getOrderFixtures();

        // THEN - each of the expected fixtures exists and has a single order item
        for (String orderId : DOUBLE_ITEM_ORDER_FIXTURES) {
            OrderFixture orderFixture = fixtures.get(orderId);
            assertNotNull(orderFixture,
                          String.format("Expected order ID '%s' to exist in fixtures but it does not. Fixtures: %s",
                                        orderId,
                                        fixtures
                          )
            );
            assertEquals(2, orderFixture.getOrderData().getCustomerOrderItemList().size());
        }
    }

    @Test
    public void getOrderFixtures_containsExpectedTripleItemOrders() {
        // GIVEN - TRIPLE_ITEM_ORDER_FIXTURES

        // WHEN
        Map<String, OrderFixture> fixtures = orderDatastore.getOrderFixtures();

        // THEN - each of the expected fixtures exists and has a single order item
        for (String orderId : TRIPLE_ITEM_ORDER_FIXTURES) {
            OrderFixture orderFixture = fixtures.get(orderId);
            assertNotNull(orderFixture,
                          String.format("Expected order ID '%s' to exist in fixtures but it does not. Fixtures: %s",
                                        orderId,
                                        fixtures
                          )
            );
            assertEquals(3, orderFixture.getOrderData().getCustomerOrderItemList().size());
        }
    }

    @Test
    public void getOrderFixturesTable_returnsTextTable_withExpectedOrders() {
        // GIVEN - all fixture order IDs

        // WHEN
        String fixturesTable = orderDatastore.getOrderFixturesTable();

        // THEN
        for (String orderId : allFixtureOrderIds) {
            Pattern orderIdPattern = Pattern.compile(substringMatchPattern(orderId),
                                                     Pattern.MULTILINE | Pattern.DOTALL);
            assertTrue(orderIdPattern.matcher(fixturesTable).matches(),
                       String.format("Expected to see order ID '%s' in fixture table, but didn't. " +
                                     "Fixture table:\n%s",
                                     orderId,
                                     fixturesTable
                       )
            );
        }
    }

    @Test
    public void getOrderFixturesTable_returnsTextTable_withExpectedColumns() {
        // GIVEN
        List<String> expectedColumns = Arrays.asList("ORDER ID",
                                                     "# ITEMS",
                                                     "# SHIPMENTS",
                                                     "# SHIPPED",
                                                     "# DELIVERED",
                                                     "DPS & OFS PROMISES AGREE?",
                                                     "PROMISE CONFIDENCE?",
                                                     "DESCRIPTION");

        // WHEN
        String fixturesTable = orderDatastore.getOrderFixturesTable();

        // THEN
        for (String columnName : expectedColumns) {
            Pattern columnPattern = Pattern.compile(substringMatchPattern(columnName),
                                                    Pattern.MULTILINE | Pattern.DOTALL);
            assertTrue(columnPattern.matcher(fixturesTable).matches(),
                       String.format("Expected to find column '%s' in fixture table, but did not. " +
                                     "Fixture table:\n%s",
                                     columnName,
                                     fixturesTable
                       )
            );
        }
    }

    /*
     * Returns a regex string to find a substring within a multi-line String.
     */
    private String substringMatchPattern(String searchString) {
        return String.format(".*%s.*", searchString);
    }

    /*
     * creates a list of consecutive order IDs from the given starting point, one for each record in the datastore.
     */
    private List<String> generateOrderIds(String starterId) {
        String orderId;

        if (null == starterId) {
            orderId = "111-7497023-2960775";
        } else {
            orderId = starterId;
        }

        String[] orderSegments = orderId.split("-");
        String prefix = String.format("%s-%s-", orderSegments[0], orderSegments[1]);
        int suffix = Integer.parseInt(orderSegments[2]);

        List<String> orderIds = new ArrayList<>();
        for (int i = 0; i < orderDatastore.getNumOrders(); i++) {
            orderIds.add(String.format("%s%d", prefix, suffix));
            suffix++;
        }

        return orderIds;
    }
}
