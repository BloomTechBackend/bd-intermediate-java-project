package com.amazon.ata.deliveringonourpromise.types;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTestNotOctaneTemplate {
    @Test
    public void getCustomerOrderItemList_returnedListIsModified_updatesOrderItemsInOrder() {
        // This test shows the undesired effect of not having defensive copying mechanism in place
        // It should be removed after the defensive copying has been put in place.

        // GIVEN
        OrderItem customerOrderItem = OrderItem.builder()
            .withCustomerOrderItemId("1")
            .build();
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(customerOrderItem);
        Order order = Order.builder()
            .withCustomerOrderItemList(orderItemList)
            .build();
        OrderItem maliciousCustomerOrderItem = OrderItem.builder()
            .withCustomerOrderItemId("2")
            .build();

        // WHEN
        List<OrderItem> customerOrderItemList = order.getCustomerOrderItemList();
        customerOrderItemList.remove(0);
        customerOrderItemList.add(maliciousCustomerOrderItem);

        // THEN
        OrderItem orderItem = order.getCustomerOrderItemList().get(0);
        assertEquals(maliciousCustomerOrderItem.getCustomerOrderItemId(), orderItem.getCustomerOrderItemId());
    }

    @Test
    public void orderId_fieldIsModifiedDirectly_compilesAndOrderIdIsModified() {
        // This test shows the undesired effect of having public class members
        // It should be removed after the members have been made private

        // GIVEN
        Order order = Order.builder()
            .withOrderId("1")
            .build();
        String maliciousOrderId = "2";

        // WHEN
        order.orderId = maliciousOrderId;

        // THEN
        assertEquals(maliciousOrderId, order.getOrderId());
    }
}
