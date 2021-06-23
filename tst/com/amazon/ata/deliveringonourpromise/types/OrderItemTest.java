package com.amazon.ata.deliveringonourpromise.types;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderItemTest {
    private String customerOrderItemId = "1";
    private String orderId = "2";
    private String asin = "3";
    private int confidence = 10;
    private boolean isConfidenceTracked = false;
    private String merchantId = "4";
    private int quantity = 103;
    private String title = "A title";

    @Test
    void equals_onEqualOrderItems_returnsTrue() {
        // GIVEN
        OrderItem orderItem1 = getGenericOrderItemBuilder().build();
        OrderItem orderItem2 = getGenericOrderItemBuilder().build();

        // WHEN
        boolean result = orderItem1.equals(orderItem2);

        // THEN
        assertTrue(
            result,
            String.format("Expected %s and %s OrderItems to be equal but they were not", orderItem1, orderItem2)
        );
    }

    @Test
    void equals_onOrderItemsWithDifferentCustomerOrderItemId_returnsFalse() {
        // GIVEN
        OrderItem orderItem1 = getGenericOrderItemBuilder().withCustomerOrderItemId("1234").build();
        OrderItem orderItem2 = getGenericOrderItemBuilder().withCustomerOrderItemId("5678").build();

        // WHEN
        boolean result = orderItem1.equals(orderItem2);

        // THEN
        assertFalse(
            result,
            String.format("Expected %s and %s OrderItems to not be equal but they were", orderItem1, orderItem2)
        );
    }

    @Test
    void equals_onOrderItemsWithDifferentOrderId_returnsFalse() {
        // GIVEN
        OrderItem orderItem1 = getGenericOrderItemBuilder().withOrderId("1234").build();
        OrderItem orderItem2 = getGenericOrderItemBuilder().withOrderId("5678").build();

        // WHEN
        boolean result = orderItem1.equals(orderItem2);

        // THEN
        assertFalse(
            result,
            String.format("Expected %s and %s OrderItems to not be equal but they were", orderItem1, orderItem2)
        );
    }

    @Test
    void equals_onOrderItemsWithDifferentAsin_returnsFalse() {
        // GIVEN
        OrderItem orderItem1 = getGenericOrderItemBuilder().withAsin("1234").build();
        OrderItem orderItem2 = getGenericOrderItemBuilder().withAsin("5678").build();

        // WHEN
        boolean result = orderItem1.equals(orderItem2);

        // THEN
        assertFalse(
            result,
            String.format("Expected %s and %s OrderItems to not be equal but they were", orderItem1, orderItem2)
        );
    }

    @Test
    void equals_onOrderItemsWithDifferentConfidence_returnsFalse() {
        OrderItem orderItem1 = getGenericOrderItemBuilder().withConfidence(10).build();
        OrderItem orderItem2 = getGenericOrderItemBuilder().withConfidence(20).build();

        // WHEN
        boolean result = orderItem1.equals(orderItem2);

        // THEN
        assertFalse(
            result,
            String.format("Expected %s and %s OrderItems to not be equal but they were", orderItem1, orderItem2)
        );
    }

    @Test
    void equals_onOrderItemsWithDifferentIsConfidenceTracked_returnsFalse() {
        // GIVEN
        OrderItem orderItem1 = getGenericOrderItemBuilder().withIsConfidenceTracked(true).build();
        OrderItem orderItem2 = getGenericOrderItemBuilder().withIsConfidenceTracked(false).build();

        // WHEN
        boolean result = orderItem1.equals(orderItem2);

        // THEN
        assertFalse(
            result,
            String.format("Expected %s and %s OrderItems to not be equal but they were", orderItem1, orderItem2)
        );
    }

    @Test
    void equals_onOrderItemsWithDifferentMerchantId_returnsFalse() {
        // GIVEN
        OrderItem orderItem1 = getGenericOrderItemBuilder().withMerchantId("1234").build();
        OrderItem orderItem2 = getGenericOrderItemBuilder().withMerchantId("5678").build();

        // WHEN
        boolean result = orderItem1.equals(orderItem2);

        // THEN
        assertFalse(
            result,
            String.format("Expected %s and %s OrderItems to not be equal but they were", orderItem1, orderItem2)
        );
    }

    @Test
    void equals_onOrderItemsWithDifferentQuantity_returnsFalse() {
        // GIVEN
        OrderItem orderItem1 = getGenericOrderItemBuilder().withQuantity(101).build();
        OrderItem orderItem2 = getGenericOrderItemBuilder().withQuantity(102).build();

        // WHEN
        boolean result = orderItem1.equals(orderItem2);

        // THEN
        assertFalse(
            result,
            String.format("Expected %s and %s OrderItems to not be equal but they were", orderItem1, orderItem2)
        );
    }

    @Test
    void equals_onOrderItemsWithDifferentTitle_returnsFalse() {
        // GIVEN
        OrderItem orderItem1 = getGenericOrderItemBuilder().withTitle("1234").build();
        OrderItem orderItem2 = getGenericOrderItemBuilder().withTitle("5678").build();

        // WHEN
        boolean result = orderItem1.equals(orderItem2);

        // THEN
        assertFalse(
            result,
            String.format("Expected %s and %s OrderItems to not be equal but they were", orderItem1, orderItem2)
        );
    }

    @Test
    void hashCode_onEqualOrderItems_returnsSameValue() {
        // GIVEN
        OrderItem orderItem1 = getGenericOrderItemBuilder().build();
        OrderItem orderItem2 = getGenericOrderItemBuilder().build();

        // THEN
        assertEquals(
            orderItem1.hashCode(),
            orderItem2.hashCode(),
            String.format(
                "Expected hashCode of %s (%d) and %s (%d) OrderItems to be equal but they were not",
                orderItem1,
                orderItem1.hashCode(),
                orderItem2,
                orderItem2.hashCode())
        );
    }

    private OrderItem.Builder getGenericOrderItemBuilder() {
        return OrderItem.builder()
                   .withCustomerOrderItemId(customerOrderItemId)
                   .withOrderId(orderId)
                   .withAsin(asin)
                   .withConfidence(confidence)
                   .withIsConfidenceTracked(isConfidenceTracked)
                   .withMerchantId(merchantId)
                   .withQuantity(quantity)
                   .withTitle(title);
    }


}
