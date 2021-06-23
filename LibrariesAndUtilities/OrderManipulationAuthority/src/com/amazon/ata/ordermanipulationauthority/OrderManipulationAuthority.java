package com.amazon.ata.ordermanipulationauthority;

import com.amazon.ata.deliveringonourpromise.data.OrderData;
import com.amazon.ata.deliveringonourpromise.data.OrderDatastore;
import com.amazon.ata.deliveringonourpromise.data.OrderItemData;
import com.amazon.ata.deliveringonourpromise.data.OrderShipmentData;

import java.util.ArrayList;
import java.util.List;

/**
 * local fake service for OrderManipulationAuthority, vending order
 * details for given order IDs.
 */
public class OrderManipulationAuthority {
    private OrderDatastore orderDatastore;

    /**
     * Constructs a new OMA, with the given order datastore.
     *
     * @param orderDatastore the order datastore to use to fetch order data
     */
    public OrderManipulationAuthority(OrderDatastore orderDatastore) {
        this.orderDatastore = orderDatastore;
    }

    /**
     * Given a customer order ID String, return the Order corresponding to that order ID,
     * or null otherwise.
     *
     * @param orderId The order ID to fetch the Order for
     * @return The Order corresponding to the order ID, or null if no Order is found.
     */
    public OrderResult getCustomerOrderByOrderId(String orderId) {
        OrderData orderData = orderDatastore.getOrderData(orderId);

        if (null == orderData) {
            return null;
        }

        return convertOrderDataToOrderResult(orderData);
    }

    /**
     * Given a customer order item ID, return the corresponding OrderResultItem, or null if no
     * such order item ID exists.
     *
     * @param orderItemId the order-item identifier to fetch
     * @return OrderResultItem corresponding to the order item ID if the order item exists, null otherwise
     */
    public OrderResultItem getCustomerOrderItemByOrderItemId(String orderItemId) {
        OrderItemData orderItemData = orderDatastore.getOrderItemData(orderItemId);

        if (null == orderItemData) {
            return null;
        }

        return convertOrderItemDataToOrderResultItem(orderItemData);
    }

    private OrderResult convertOrderDataToOrderResult(OrderData orderData) {
        List<OrderResultItem> orderResultItems = new ArrayList<>();
        for (OrderItemData orderItemData : orderData.getCustomerOrderItemList()) {
            if (null == orderItemData) {
                continue;
            }
            orderResultItems.add(convertOrderItemDataToOrderResultItem(orderItemData));
        }

        List<OrderShipment> orderShipments = new ArrayList<>();
        for (OrderShipmentData orderShipmentData : orderData.getCustomerShipments()) {
            if (null == orderShipmentData) {
                continue;
            }
            orderShipments.add(convertOrderShipmentDataToOrderResultShipment(orderShipmentData));
        }

        return OrderResult.builder()
                   .withOrderId(orderData.getOrderId())
                   .withCustomerOrderItemList(orderResultItems)
                   .withOrderShipmentList(orderShipments)
                   .withCondition(OrderCondition.fromCode(orderData.getCondition()))
                   .withCustomerId(orderData.getCustomerId())
                   .withOrderDate(orderData.getOrderDate())
                   .withShipOption(orderData.getShipOption())
                   .withMarketplaceId(orderData.getMarketplaceId())
                   .build();
    }

    private OrderShipment convertOrderShipmentDataToOrderResultShipment(OrderShipmentData orderShipmentData) {
        List<OrderShipment.ShipmentItem> shipmentItems = new ArrayList<>();
        for (OrderShipmentData.CustomerShipmentItemData shipItemData : orderShipmentData.getCustomerShipmentItems()) {
            if (null == shipItemData) {
                continue;
            }
            shipmentItems.add(convertShipmentItemDataToShipmentItem(shipItemData));
        }

        return OrderShipment.builder()
                   .withShipmentId(orderShipmentData.getShipmentId())
                   .withCondition(orderShipmentData.getCondition())
                   .withCreationDate(orderShipmentData.getCreationDate())
                   .withCustomerShipmentItems(shipmentItems)
                   .withDeliveryDate(orderShipmentData.getDeliveryDate())
                   .withShipDate(orderShipmentData.getShipDate())
                   .withShipmentShipOption(orderShipmentData.getShipmentShipOption())
                   .withWarehouseId(orderShipmentData.getWarehouseId())
                   .withZip(orderShipmentData.getZip())
                   .build();
    }

    private OrderShipment.ShipmentItem convertShipmentItemDataToShipmentItem(
        OrderShipmentData.CustomerShipmentItemData shipmentItemData
    ) {
        return new OrderShipment.ShipmentItem(
            shipmentItemData.getCustomerOrderItemId(),
            shipmentItemData.getQuantity()
        );
    }

    private OrderResultItem convertOrderItemDataToOrderResultItem(OrderItemData orderItemData) {
        return OrderResultItem.builder()
                   .withCustomerOrderItemId(orderItemData.getCustomerOrderItemId())
                   .withOrderId(orderItemData.getOrderId())
                   .withApprovalDate(orderItemData.getApprovalDate())
                   .withAsin(orderItemData.getAsin())
                   .withMerchantId(orderItemData.getMerchantId())
                   .withQuantity(orderItemData.getQuantity())
                   .withSupplyCode(orderItemData.getSupplyCode())
                   .withSupplyCodeDate(orderItemData.getSupplyCodeDate())
                   .withTitle(orderItemData.getTitle())
                   .withIsConfidenceTracked(orderItemData.isConfidenceTracked())
                   .withConfidence(orderItemData.getConfidence())
                   .build();
    }
}
