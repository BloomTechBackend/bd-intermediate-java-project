package com.amazon.ata.deliveringonourpromise.ordermanipulationauthority;

import com.amazon.ata.ordermanipulationauthority.OrderManipulationAuthority;
import com.amazon.ata.ordermanipulationauthority.OrderResult;
import com.amazon.ata.ordermanipulationauthority.OrderResultItem;

/**
 * Client for accessing the OrderManipulationAuthority service.
 */
public class OrderManipulationAuthorityClient {
    private OrderManipulationAuthority omaService;

    /**
     * Create new client that calls OMA with the given service object.
     * @param service The OrderManipulationAuthority that this client will call
     */
    public OrderManipulationAuthorityClient(OrderManipulationAuthority service) {
        this.omaService = service;
    }

    /**
     * Fetches the Order for the given order ID.
     * @param orderId String representing the order ID to fetch the order for.
     * @return the Order for the given order ID if found; or null, otherwise
     */
    public OrderResult getCustomerOrderByOrderId(String orderId) {
        return omaService.getCustomerOrderByOrderId(orderId);
    }

    /**
     * Fetches the OrderItem for the given order item ID, if it exists.
     * @param orderItemId the order item ID to fetch the order item ID for
     * @return the OrderItem for the given order Item ID if found; or null, otherwise
     */
    public OrderResultItem getCustomerOrderItemByOrderItemId(String orderItemId) {
        return omaService.getCustomerOrderItemByOrderItemId(orderItemId);
    }
}
