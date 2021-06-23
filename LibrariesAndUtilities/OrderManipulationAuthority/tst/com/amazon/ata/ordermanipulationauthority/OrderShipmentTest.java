package com.amazon.ata.ordermanipulationauthority;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderShipmentTest {

    @Test
    public void includesOrderItem_returnsTrueWhenItemIncluded() {
        // GIVEN
        String orderItemId = "111-7497023-2960780";
        List<OrderShipment.ShipmentItem> shipmentItems =
            Arrays.asList(new OrderShipment.ShipmentItem(orderItemId, 1),
                          new OrderShipment.ShipmentItem("111-7497023-9999999", 2));
        OrderShipment shipmentData = OrderShipment.builder()
                                             .withCustomerShipmentItems(shipmentItems)
                                             .build();

        // WHEN
        boolean includesOrderItem = shipmentData.includesOrderItem(orderItemId);

        // THEN
        assertTrue(includesOrderItem,
                   String.format("Expected shipment data %s to include order item ID '%s' " +
                                 "in a shipment item, but it does not",
                                 shipmentData.toString(),
                                 orderItemId
                   )
        );
    }

    @Test
    public void includesOrderItem_returnsFalseWhenItemNotIncluded() {
        // GIVEN
        String orderItemId = "111-7497023-2960780";
        List<OrderShipment.ShipmentItem> shipmentItems =
            Arrays.asList(new OrderShipment.ShipmentItem("111-7497023-9999999", 1),
                          new OrderShipment.ShipmentItem("111-7497023-1111111", 2));
        OrderShipment shipmentData = OrderShipment.builder()
                                             .withCustomerShipmentItems(shipmentItems)
                                             .build();

        // WHEN
        boolean includesOrderItem = shipmentData.includesOrderItem(orderItemId);

        // THEN
        assertFalse(includesOrderItem,
                    String.format("Expected shipment data %s to NOT include order item ID '%s' " +
                                  "in a shipment item, but it DOES",
                                  shipmentData.toString(),
                                  orderItemId
                    )
        );
    }
}
