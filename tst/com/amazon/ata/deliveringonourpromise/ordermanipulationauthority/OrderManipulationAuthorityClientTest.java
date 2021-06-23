package com.amazon.ata.deliveringonourpromise.ordermanipulationauthority;

import com.amazon.ata.ordermanipulationauthority.OrderManipulationAuthority;
import com.amazon.ata.ordermanipulationauthority.OrderResult;
import com.amazon.ata.ordermanipulationauthority.OrderResultItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderManipulationAuthorityClientTest {
    private OrderManipulationAuthorityClient client;
    private OrderManipulationAuthority mockOrderManipulationAuthority;
    private String orderId;
    private String orderItemId;
    private OrderResult orderResult;
    private OrderResultItem orderResultItem;
    private String invalidOrderId;
    private String invalidOrderItemId;

    @BeforeEach
    private void createClient() {
        mockOrderManipulationAuthority = mock(OrderManipulationAuthority.class);
        client = new OrderManipulationAuthorityClient(mockOrderManipulationAuthority);
        orderId = "111-7497023-2960775";
        orderItemId = "20655079937481";
        invalidOrderId = "123";
        invalidOrderItemId = "01";

        orderResultItem = OrderResultItem.builder()
                              .withCustomerOrderItemId(orderItemId)
                              .withOrderId(orderId).build();
        orderResult = OrderResult.builder()
                          .withOrderId(orderId)
                          .withCustomerOrderItemList(Arrays.asList(orderResultItem))
                          .build();

        when(mockOrderManipulationAuthority.getCustomerOrderByOrderId(orderId)).thenReturn(orderResult);
        when(mockOrderManipulationAuthority.getCustomerOrderItemByOrderItemId(orderItemId)).thenReturn(orderResultItem);
        when(mockOrderManipulationAuthority.getCustomerOrderByOrderId(invalidOrderId)).thenReturn(null);
        when(mockOrderManipulationAuthority.getCustomerOrderItemByOrderItemId(invalidOrderItemId)).thenReturn(null);
    }

    @Test
    public void getCustomerOrderByOrderId_validOrderId_returnsOrderResult() {
        // WHEN
        OrderResult result = client.getCustomerOrderByOrderId(orderId);

        // THEN
        assertEquals(orderResult, result);
    }

    @Test
    public void getCustomerOrderItemByOrderItemId_validOrderItemId_returnsOrderResultItem() {
        // WHEN
        OrderResultItem result = client.getCustomerOrderItemByOrderItemId(orderItemId);

        // THEN
        assertEquals(orderResultItem, result);
    }

    @Test
    public void getCustomerOrderByOrderId_invalidOrderId_returnsNull() {
        // WHEN
        OrderResult result = client.getCustomerOrderByOrderId(invalidOrderId);

        // THEN
        assertNull(result);
    }

    @Test
    public void getCustomerOrderItemByOrderItemId_invalidOrderItemId_returnsNull() {
        // WHEN
        OrderResultItem result = client.getCustomerOrderItemByOrderItemId(invalidOrderItemId);

        // THEN
        assertNull(result);
    }

}
