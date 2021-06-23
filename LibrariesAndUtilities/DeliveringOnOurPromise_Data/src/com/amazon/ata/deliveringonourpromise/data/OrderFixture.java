package com.amazon.ata.deliveringonourpromise.data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a specific data case that we've intentionally created, and associated with a specific
 * order ID. An OrderFixture contains an OrderData and a String indicating the data scenario that it represents.
 */
public class OrderFixture {
    private OrderData orderData;
    private String dataDescription;

    public OrderFixture(OrderData orderData, String dataDescription) {
        this.orderData = orderData;
        this.dataDescription = dataDescription;
    }

    public OrderData getOrderData() {
        return orderData;
    }

    public String getDataDescription() {
        return dataDescription;
    }

    /**
     * Returns the order ID for this fixture.
     *
     * @return order ID for this OrderData, or null if no there is no OrderData
     */
    public String getOrderId() {
        if (null == orderData) {
            return null;
        }

        return orderData.getOrderId();
    }

    /**
     * Returns a list of order item IDs included in this fixture.
     *
     * @return List of order item ID strings that are in this fixture's OrderData; or an empty list if there is no
     * OrderData
     */
    public List<String> getOrderItemIds() {
        if (null == orderData) {
            return Collections.emptyList();
        }
        return orderData.getCustomerOrderItemList().stream()
                   .map(OrderItemData::getCustomerOrderItemId)
                   .collect(Collectors.toList());
    }
}
