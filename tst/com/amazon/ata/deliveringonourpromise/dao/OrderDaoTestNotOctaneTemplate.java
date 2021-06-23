package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Order;
import com.amazon.ata.deliveringonourpromise.types.OrderItem;
import com.amazon.ata.ordermanipulationauthority.OrderCondition;
import com.amazon.ata.ordermanipulationauthority.OrderResult;
import com.amazon.ata.ordermanipulationauthority.OrderResultItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class OrderDaoTestNotOctaneTemplate {

    private OrderDao dao;
    private OrderManipulationAuthorityClient mockOmaClient;

    @BeforeEach
    private void setup() {
        mockOmaClient = mock(OrderManipulationAuthorityClient.class);
        dao = new OrderDao(mockOmaClient);
    }

    @Test
    public void get_nullOrderId_returnsNull() {
        // GIVEN
        when(mockOmaClient.getCustomerOrderByOrderId(null)).thenReturn(null);

        // WHEN
        Order order = dao.get(null);

        // THEN
        assertNull(order);
    }

    @Test
    public void get_invalidOrderId_returnsNull() {
        // GIVEN
        String invalidOrderId = "123";
        when(mockOmaClient.getCustomerOrderByOrderId(invalidOrderId)).thenReturn(null);

        // WHEN
        Order order = dao.get(invalidOrderId);

        // THEN
        assertNull(order);
    }

    @Test
    public void get_validOrder_returnsOrderResultWithCorrectOrderId() {
        // GIVEN
        String orderId = "747-3857033-2947383";
        OrderResult orderResult = createOrderResult(orderId);
        when(mockOmaClient.getCustomerOrderByOrderId(anyString())).thenReturn(orderResult);

        // WHEN
        Order order = dao.get(orderId);

        // THEN
        assertEquals(orderId, order.getOrderId());
    }

    @Test
    public void get_validOrder_returnsOrderResultWithOrderItemsWithSameOrderId() {
        // GIVEN
        String orderId = "747-3857033-2947383";
        OrderResult orderResult = createOrderResult(orderId);
        when(mockOmaClient.getCustomerOrderByOrderId(anyString())).thenReturn(orderResult);

        // WHEN
        Order order = dao.get(orderId);

        // THEN
        for (OrderItem item : order.getCustomerOrderItemList()) {
            assertEquals(orderId, item.getOrderId());
        }
    }

    @Test
    public void get_validOrder_returnsOrderResultWithCorrectCustomerId() {
        // GIVEN
        String orderId = "747-3857033-2947383";
        OrderResult orderResult = createOrderResult(orderId);
        when(mockOmaClient.getCustomerOrderByOrderId(anyString())).thenReturn(orderResult);

        // WHEN
        Order order = dao.get(orderId);

        // THEN
        assertEquals(orderResult.getCustomerId(), order.getCustomerId());
    }

    @Test
    public void get_validOrder_returnsOrderResultWithCorrectMarketplaceId() {
        // GIVEN
        String orderId = "747-3857033-2947383";
        OrderResult orderResult = createOrderResult(orderId);
        when(mockOmaClient.getCustomerOrderByOrderId(anyString())).thenReturn(orderResult);

        // WHEN
        Order order = dao.get(orderId);

        // THEN
        assertEquals(orderResult.getMarketplaceId(), order.getMarketplaceId());
    }

    @Test
    public void get_validOrder_returnsOrderResultWithCorrectCondition() {
        // GIVEN
        String orderId = "747-3857033-2947383";
        OrderResult orderResult = createOrderResult(orderId);
        when(mockOmaClient.getCustomerOrderByOrderId(anyString())).thenReturn(orderResult);

        // WHEN
        Order order = dao.get(orderId);

        // THEN
        assertEquals(orderResult.getCondition(), order.getCondition());
    }

    @Test
    public void get_validOrder_returnsOrderResultWithCorrectOrderDate() {
        // GIVEN
        String orderId = "747-3857033-2947383";
        OrderResult orderResult = createOrderResult(orderId);
        when(mockOmaClient.getCustomerOrderByOrderId(anyString())).thenReturn(orderResult);

        // WHEN
        Order order = dao.get(orderId);

        // THEN
        assertEquals(orderResult.getOrderDate(), order.getOrderDate());
    }

    @Test
    public void get_validOrder_returnsOrderResultWithCorrectShipOption() {
        // GIVEN
        String orderId = "747-3857033-2947383";
        OrderResult orderResult = createOrderResult(orderId);
        when(mockOmaClient.getCustomerOrderByOrderId(anyString())).thenReturn(orderResult);

        // WHEN
        Order order = dao.get(orderId);

        // THEN
        assertEquals(orderResult.getShipOption(), order.getShipOption());
    }

    @Test
    public void get_validOrder_returnsOrderResultItemsWithCorrectCustomerOrderItemId() {
        // GIVEN
        String orderId = "747-3857033-2947383";
        OrderResult orderResult = createOrderResult(orderId);
        OrderResultItem orderResultItem = orderResult.getCustomerOrderItemList().get(0);
        when(mockOmaClient.getCustomerOrderByOrderId(anyString())).thenReturn(orderResult);

        // WHEN
        Order order = dao.get(orderId);

        // THEN
        OrderItem orderItem = order.getCustomerOrderItemList().get(0);
        assertEquals(orderResultItem.getCustomerOrderItemId(), orderItem.getCustomerOrderItemId());
    }

    @Test
    public void get_validOrder_returnsOrderResultItemsWithCorrectAsin() {
        // GIVEN
        String orderId = "747-3857033-2947383";
        OrderResult orderResult = createOrderResult(orderId);
        OrderResultItem orderResultItem = orderResult.getCustomerOrderItemList().get(0);
        when(mockOmaClient.getCustomerOrderByOrderId(anyString())).thenReturn(orderResult);

        // WHEN
        Order order = dao.get(orderId);

        // THEN
        OrderItem orderItem = order.getCustomerOrderItemList().get(0);
        assertEquals(orderResultItem.getAsin(), orderItem.getAsin());
    }

    @Test
    public void get_validOrder_returnsOrderResultItemsWithCorrectMerchantId() {
        // GIVEN
        String orderId = "747-3857033-2947383";
        OrderResult orderResult = createOrderResult(orderId);
        OrderResultItem orderResultItem = orderResult.getCustomerOrderItemList().get(0);
        when(mockOmaClient.getCustomerOrderByOrderId(anyString())).thenReturn(orderResult);

        // WHEN
        Order order = dao.get(orderId);

        // THEN
        OrderItem orderItem = order.getCustomerOrderItemList().get(0);
        assertEquals(orderResultItem.getMerchantId(), orderItem.getMerchantId());
    }

    @Test
    public void get_validOrder_returnsOrderResultItemsWithCorrectQuantity() {
        // GIVEN
        String orderId = "747-3857033-2947383";
        OrderResult orderResult = createOrderResult(orderId);
        OrderResultItem orderResultItem = orderResult.getCustomerOrderItemList().get(0);
        when(mockOmaClient.getCustomerOrderByOrderId(anyString())).thenReturn(orderResult);

        // WHEN
        Order order = dao.get(orderId);

        // THEN
        OrderItem orderItem = order.getCustomerOrderItemList().get(0);
        assertEquals(orderResultItem.getQuantity(), orderItem.getQuantity());
    }

    @Test
    public void get_validOrder_returnsOrderResultItemsWithCorrectTitle() {
        // GIVEN
        String orderId = "747-3857033-2947383";
        OrderResult orderResult = createOrderResult(orderId);
        OrderResultItem orderResultItem = orderResult.getCustomerOrderItemList().get(0);
        when(mockOmaClient.getCustomerOrderByOrderId(anyString())).thenReturn(orderResult);

        // WHEN
        Order order = dao.get(orderId);

        // THEN
        OrderItem orderItem = order.getCustomerOrderItemList().get(0);
        assertEquals(orderResultItem.getTitle(), orderItem.getTitle());
    }

    private OrderResult createOrderResult(String orderId) {
        OrderResultItem orderResultItem = OrderResultItem.builder()
                                              .withOrderId(orderId)
                                              .withAsin("B00JLTOZVQ")
                                              .withMerchantId("14263472715")
                                              .withQuantity(2)
                                              .withTitle("Java Performance: The Definitive Guide")
                                              .build();

        return OrderResult.builder()
                   .withOrderId(orderId)
                   .withCustomerOrderItemList(Arrays.asList(orderResultItem))
                   .withCustomerId("375944378")
                   .withMarketplaceId("1 - US")
                   .withCondition(OrderCondition.CLOSED)
                   .withOrderDate(ZonedDateTime.now().minusDays(1))
                   .withShipOption("second")
                   .build();
    }
}
