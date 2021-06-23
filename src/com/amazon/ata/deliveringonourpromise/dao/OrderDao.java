package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Order;
import com.amazon.ata.deliveringonourpromise.types.OrderItem;
import com.amazon.ata.order.OrderFieldValidator;
import com.amazon.ata.ordermanipulationauthority.OrderResult;
import com.amazon.ata.ordermanipulationauthority.OrderResultItem;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for orders.
 */
public class OrderDao implements ReadOnlyDao<String, Order> {

    private OrderManipulationAuthorityClient omaClient;

    /**
     * OrderDao constructor.
     *
     * @param client OrderManipulationAuthorityClient to pass in
     */
    public OrderDao(OrderManipulationAuthorityClient client) {
        omaClient = client;
    }

    /**
     * Returns an Order object corresponding to the provided orderId. If no order can be
     * found for the provided orderId, a null Order will be returned.
     *
     * @param orderId the id of the order to be retrieved.
     * @return the order with the corresponding orderId.
     */
    @Override
    public Order get(String orderId) {
        OrderResult omaOrder = omaClient.getCustomerOrderByOrderId(orderId);

        if (null == omaOrder) {
            return null;
        }

        if (! new OrderFieldValidator().isValidOrderId(orderId)) {
            return null;
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderResultItem orderResultItem : omaOrder.getCustomerOrderItemList()) {
            orderItems.add(convertToOrderItem(orderResultItem));
        }

        Order.Builder orderBuilder = Order.builder()
                                         .withOrderId(omaOrder.getOrderId())
                                         .withCustomerId(omaOrder.getCustomerId())
                                         .withMarketplaceId(omaOrder.getMarketplaceId())
                                         .withCondition(omaOrder.getCondition())
                                         .withCustomerOrderItemList(orderItems)
                                         .withOrderDate(omaOrder.getOrderDate())
                                         .withShipOption(omaOrder.getShipOption());

        return orderBuilder.build();
    }

    private OrderItem convertToOrderItem(OrderResultItem orderResultItem) {
        return OrderItem.builder()
                   .withCustomerOrderItemId(orderResultItem.getCustomerOrderItemId())
                   .withOrderId(orderResultItem.getOrderId())
                   .withAsin(orderResultItem.getAsin())
                   .withMerchantId(orderResultItem.getMerchantId())
                   .withQuantity(orderResultItem.getQuantity())
                   .withTitle(orderResultItem.getTitle())
                   .withIsConfidenceTracked(orderResultItem.isConfidenceTracked())
                   .withConfidence(orderResultItem.getConfidence())
                   .build();
    }

}
