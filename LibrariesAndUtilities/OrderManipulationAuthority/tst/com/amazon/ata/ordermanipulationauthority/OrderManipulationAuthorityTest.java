package com.amazon.ata.ordermanipulationauthority;

import com.amazon.ata.deliveringonourpromise.data.OrderDatastore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderManipulationAuthorityTest {
    private OrderManipulationAuthority oma;

    @BeforeEach
    private void setup() {
        oma = new OrderManipulationAuthority(OrderDatastore.getDatastore());
    }

    @Test
    public void getCustomerOrderByOrderId_returnsNullForUnrecognizedOrderId() {
        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId("NOT AN ORDER ID");

        // THEN
        assertNull(order, String.format("Expected order returned to be null, but is instead: %s", order));
    }

    @Test
    public void getCustomerOrderByOrderId_validOrderIdReturnsOrderWithOrderId() {
        // GIVEN
        String orderId = "101-9374937-9275625";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        assertNotNull(order);
        assertEquals(orderId, order.getOrderId());
    }

    @Test
    public void getCustomerOrderByOrderId_validOrderIdOrderIdsMatch() {
        // GIVEN
        String orderId = "101-9374937-9275620";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderResultItem item : order.getCustomerOrderItemList()) {
            assertEquals(orderId, item.getOrderId());
        }
    }

    @Test
    public void getCustomerOrderByOrderId_hasCondition() {
        // GIVEN
        String orderId = "101-9374937-9275626";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        assertNotNull(order.getCondition());
    }

    @Test
    public void getCustomerOrderByOrderId_hasCustomerId() {
        // GIVEN
        String orderId = "101-9374937-9275627";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        assertNotNull(order.getCustomerId());
    }

    @Test
    public void getCustomerOrderByOrderId_hasOrderDate() {
        // GIVEN
        String orderId = "101-9374937-9275628";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        assertNotNull(order.getOrderDate());
    }

    @Test
    public void getCustomerOrderByOrderId_hasShipOption() {
        // GIVEN
        String orderId = "101-9374937-9275629";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        assertNotNull(order.getShipOption());
    }

    @Test
    public void getCustomerOrderByOrderId_hasMarketplaceId() {
        // GIVEN
        String orderId = "101-9374937-9275630";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        assertNotNull(order.getMarketplaceId());
    }

    // order items

    @Test
    public void getCustomerOrderByOrderId_orderItemsHaveApprovalDate() {
        // GIVEN
        String orderId = "101-9374937-9275632";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderResultItem item : order.getCustomerOrderItemList()) {
            assertNotNull(item.getApprovalDate());
        }
    }

    @Test
    public void getCustomerOrderByOrderId_orderItemsHaveMerchantId() {
        // GIVEN
        String orderId = "101-9374937-9275633";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderResultItem item : order.getCustomerOrderItemList()) {
            assertNotNull(item.getMerchantId());
        }
    }

    @Test
    public void getCustomerOrderByOrderId_orderItemsHaveAsin() {
        // GIVEN
        String orderId = "101-9374937-9275634";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderResultItem item : order.getCustomerOrderItemList()) {
            assertNotNull(item.getAsin());
        }
    }

    @Test
    public void getCustomerOrderByOrderId_orderItemsHaveSupplyCode() {
        // GIVEN
        String orderId = "101-9374937-9275635";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderResultItem item : order.getCustomerOrderItemList()) {
            assertNotNull(item.getSupplyCode());
        }
    }

    @Test
    public void getCustomerOrderByOrderId_orderItemsHaveTitle() {
        // GIVEN
        String orderId = "101-9374937-9275636";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderResultItem item : order.getCustomerOrderItemList()) {
            assertNotNull(item.getTitle());
        }

    }

    @Test
    public void getCustomerOrderByOrderId_orderItemsHaveSupplyCodeDate() {
        // GIVEN
        String orderId = "101-9374937-9275637";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderResultItem item : order.getCustomerOrderItemList()) {
            assertNotNull(item.getSupplyCodeDate());
        }
    }

    // order shipments

    @Test
    public void getCustomerOrderByOrderId_orderShipmentsHaveShipmentId() {
        // GIVEN
        String orderId = "101-9374937-9275638";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderShipment shipment : order.getOrderShipmentList()) {
            assertNotNull(shipment.getShipmentId());
        }
    }

    @Test
    public void getCustomerOrderByOrderId_orderShipmentsHaveZip() {
        // GIVEN
        String orderId = "101-9374937-9275639";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderShipment shipment : order.getOrderShipmentList()) {
            assertNotNull(shipment.getZip());
        }
    }

    @Test
    public void getCustomerOrderByOrderId_orderShipmentsHaveCondition() {
        // GIVEN
        String orderId = "101-9374937-9275640";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderShipment shipment : order.getOrderShipmentList()) {
            assertNotNull(shipment.getCondition());
        }
    }

    @Test
    public void getCustomerOrderByOrderId_orderShipmentsHaveWarehouseId() {
        // GIVEN
        String orderId = "101-9374937-9275641";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderShipment shipment : order.getOrderShipmentList()) {
            assertNotNull(shipment.getWarehouseId());
        }
    }

    @Test
    public void getCustomerOrderByOrderId_orderShipmentsHaveShipmentItems() {
        // GIVEN
        String orderId = "101-9374937-9275642";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderShipment shipment : order.getOrderShipmentList()) {
            assertFalse(shipment.getCustomerShipmentItems().isEmpty());
        }
    }

    @Test
    public void getCustomerOrderByOrderId_orderShipmentsHaveCreationDate() {
        // GIVEN
        String orderId = "101-9374937-9275643";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderShipment shipment : order.getOrderShipmentList()) {
            assertNotNull(shipment.getCreationDate());
        }
    }

    @Test
    public void getCustomerOrderByOrderId_orderShipmentsHaveShipOption() {
        // GIVEN
        String orderId = "101-9374937-9275644";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderShipment shipment : order.getOrderShipmentList()) {
            assertNotNull(shipment.getShipmentShipOption());
        }
    }

    // shipment items

    @Test
    public void getCustomerOrderByOrderId_allShipmentItemsHaveOrderItemId() {
        // GIVEN
        String orderId = "101-9374937-9275645";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderShipment shipment : order.getOrderShipmentList()) {
            for (OrderShipment.ShipmentItem shipmentItem : shipment.getCustomerShipmentItems()) {
                assertNotNull(shipmentItem.getCustomerOrderItemId());
            }
        }
    }

    @Test
    public void getCustomerOrderByOrderId_allShipmentItemsHavePositiveQuantity() {
        // GIVEN
        String orderId = "101-9374937-9275646";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        for (OrderShipment shipment : order.getOrderShipmentList()) {
            for (OrderShipment.ShipmentItem shipmentItem : shipment.getCustomerShipmentItems()) {
                assertTrue(shipmentItem.getQuantity() > 0);
            }
        }
    }

    @Test
    public void getCustomerOrderByOrderId_allOrderItemsAreInShipmentsSomewhere() {
        // GIVEN
        String orderId = "101-9374937-9275648";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        Set<String> orderItemIds = order.getCustomerOrderItemList().stream()
                                       .map(OrderResultItem::getCustomerOrderItemId)
                                       .collect(Collectors.toSet());
        Set<String> shipmentOrderItemIds = new HashSet<>();
        for (OrderShipment shipment : order.getOrderShipmentList()) {
            shipmentOrderItemIds.addAll(
                shipment.getCustomerShipmentItems().stream()
                    .map(OrderShipment.ShipmentItem::getCustomerOrderItemId)
                    .collect(Collectors.toSet())
            );
        }

        assertEquals(orderItemIds, shipmentOrderItemIds,
                     String.format("Expected to find all order item IDs (%s) in shipment items, " +
                                   "shipment item order item IDs: %s",
                                   orderItemIds.toString(),
                                   shipmentOrderItemIds.toString()
                     )
        );
    }

    @Test
    public void getCustomerOrderByOrderId_shipmentItemQuantitiesLessThanOrEqualOrderItemQuantities() {
        // GIVEN
        String orderId = "101-9374937-9275650";

        // WHEN
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);

        // THEN
        Map<String, Integer> orderItemIdsToQuantities =
            order.getCustomerOrderItemList().stream()
                .collect(Collectors.toMap(OrderResultItem::getCustomerOrderItemId, OrderResultItem::getQuantity));

        for (OrderShipment shipment : order.getOrderShipmentList()) {
            for (OrderShipment.ShipmentItem shipmentItem : shipment.getCustomerShipmentItems()) {
                int orderItemQuantity = orderItemIdsToQuantities.get(shipmentItem.getCustomerOrderItemId());
                assertTrue(shipmentItem.getQuantity() <= orderItemQuantity,
                           String.format("Expected shipment '%s' with shipment item with order item ID '%s' with a " +
                                         "quantity (%d) less than or equal to order item's quantity (%d)",
                                         shipment.getShipmentId(),
                                         shipmentItem.getCustomerOrderItemId(),
                                         shipmentItem.getQuantity(),
                                         orderItemIdsToQuantities.get(shipmentItem.getCustomerOrderItemId()
                                         )
                           )
                );
            }
        }
    }

    // getCustomerOrderItemByOrderItemId

    @Test
    public void getCustomerOrderItemByOrderItemId_returnsNullOnNonexistentOrderItemId() {
        // GIVEN
        String orderItemId = "20";

        // WHEN
        OrderResultItem orderItem = oma.getCustomerOrderItemByOrderItemId(orderItemId);

        // THEN
        assertNull(orderItem);
    }

    @Test
    public void getCustomerOrderItemByOrderItemId_orderItemHasCorrectOrderItemId() {
        // GIVEN
        String orderId = "101-9374937-9275651";
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);
        String orderItemId = order.getCustomerOrderItemList().get(0).getCustomerOrderItemId();

        // WHEN
        OrderResultItem orderItem = oma.getCustomerOrderItemByOrderItemId(orderItemId);

        // THEN
        assertEquals(orderItemId, orderItem.getCustomerOrderItemId(),
                     String.format("Expected order item's order item ID to match the order item ID passed to OMA " +
                                   "('%s'), but returned order item was: %s",
                                   orderItemId,
                                   orderItem.toString()
                     )
        );
    }

    @Test
    public void getCustomerOrderItemByOrderItemId_orderItemExistsInOrder() {
        // GIVEN
        String orderId = "101-9374937-9275652";
        OrderResult order = oma.getCustomerOrderByOrderId(orderId);
        String orderItemId = order.getCustomerOrderItemList().get(0).getCustomerOrderItemId();

        // WHEN
        OrderResultItem orderItem = oma.getCustomerOrderItemByOrderItemId(orderItemId);

        // THEN
        Set<String> orderItemIds = order.getCustomerOrderItemList().stream()
                                       .map(OrderResultItem::getCustomerOrderItemId)
                                       .collect(Collectors.toSet());
        assertTrue(orderItemIds.contains(orderItem.getCustomerOrderItemId()),
                   String.format("Expected the order item ID passed to OMA ('%s') to be in the order ('%s') " +
                                 "corresponding to that order item ID, but order's order item IDs were %s",
                                 orderItemId,
                                 order.getOrderId(),
                                 order.getCustomerOrderItemList().stream()
                                     .map(OrderResultItem::getCustomerOrderItemId)
                                     .toString()
                   )
        );
    }
}
