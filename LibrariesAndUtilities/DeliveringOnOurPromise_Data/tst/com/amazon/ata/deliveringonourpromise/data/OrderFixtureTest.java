package com.amazon.ata.deliveringonourpromise.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderFixtureTest {
    private OrderFixture nullOrderDataFixture;
    private OrderData nullOrderData;

    private OrderFixture populatedOrderDataFixture;
    private String orderId;
    private String orderItemId1;
    private String orderItemId2;
    private OrderData populatedOrderData;
    private String populatedDescription;

    @BeforeEach
    public void createOrderFixtures() {
        nullOrderData = null;

        orderId = "123-1111111-1111111";
        orderItemId1 = "17393748871039";
        orderItemId2 = "17393748871040";
        populatedDescription = "populated order";

        List<OrderItemData> orderItems = new ArrayList<>();
        orderItems.add(OrderItemData.builder().withCustomerOrderItemId(orderItemId1).build());
        orderItems.add(OrderItemData.builder().withCustomerOrderItemId(orderItemId2).build());

        populatedOrderData = OrderData.builder()
                                 .withOrderId(orderId)
                                 .withCustomerOrderItemList(orderItems).build();

        nullOrderDataFixture = new OrderFixture(nullOrderData, "null order");
        populatedOrderDataFixture = new OrderFixture(populatedOrderData, populatedDescription);
    }

    @Test
    public void getOrderId_nullOrderData_returnsNull() {
        // GIVEN - OrderFixture with null OrderData

        // WHEN
        String orderId = nullOrderDataFixture.getOrderId();

        // THEN - no exception plus
        assertNull(orderId);
    }

    @Test
    public void getOrderId_populatedOrderData_returnsOrderId() {
        // GIVEN - OrderFixture with populated OrderData

        // WHEN
        String result = populatedOrderDataFixture.getOrderId();

        // THEN
        assertEquals(orderId, result);
    }

    @Test
    public void getOrderItemIds_nullOrderData_returnsEmptyList() {
        // GIVEN - OrderFixture with null OrderData

        // WHEN
        List<String> orderIds = nullOrderDataFixture.getOrderItemIds();

        // THEN - no exception plus
        assertTrue(orderIds.isEmpty());
    }

    @Test
    public void getOrderItemIds_populatedOrderData_returnsOrderItemIds() {
        // GIVEN - OrderFixture with populated OrderData

        // WHEN
        List<String> results = populatedOrderDataFixture.getOrderItemIds();

        // THEN
        assertEquals(new HashSet<>(Arrays.asList(orderItemId1, orderItemId2)),
                     new HashSet<>(results));
    }
}
