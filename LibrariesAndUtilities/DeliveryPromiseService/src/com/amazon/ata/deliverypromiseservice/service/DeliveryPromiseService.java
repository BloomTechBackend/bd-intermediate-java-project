package com.amazon.ata.deliverypromiseservice.service;

import com.amazon.ata.deliveringonourpromise.data.OrderData;
import com.amazon.ata.deliveringonourpromise.data.OrderDatastore;
import com.amazon.ata.deliveringonourpromise.data.OrderItemData;
import com.amazon.ata.deliveringonourpromise.data.OrderShipmentData;

import java.time.ZonedDateTime;

/**
 * Mocks DeliveryPromiseService, returning Delivery Promises for customer order item IDs.
 */
public class DeliveryPromiseService {
    private static final String CUSTOMER_ORDER_ID_PATTERN = "-?\\d+(\\.\\d+)?";

    private OrderDatastore orderDatastore;

    /**
     * Constructs a DeliveryPromiseService instance, which will used the provided OrderDatastore
     * to ensure consistent order data.
     *
     * @param orderDatastore The order datastore to use for fetching order/item data
     */
    public DeliveryPromiseService(OrderDatastore orderDatastore) {
        this.orderDatastore = orderDatastore;
    }

    /**
     * Given a customer order ID String, return the Delivery corresponding to that order ID,
     * or null otherwise.
     *
     * @param customerOrderItemId The order ID to fetch the Delivery for
     * @return The delivery promise corresponding to the order ID, or null if no promise is found.
     */
    public DeliveryPromise getDeliveryPromise(String customerOrderItemId) {
        OrderItemData orderItemData = orderDatastore.getOrderItemData(customerOrderItemId);
        if (null == orderItemData) {
            return null;
        }

        OrderData orderData = orderDatastore.getOrderData(orderItemData.getOrderId());
        if (null == orderData) {
            return null;
        }

        ZonedDateTime orderDate = orderData.getOrderDate();
        ZonedDateTime promiseEffectiveDate = orderDate.plusHours(1);
        ZonedDateTime promiseLatestArrivalDate = promiseEffectiveDate.plusDays(2);
        ZonedDateTime promiseLatestShipDate = promiseLatestArrivalDate.minusHours(18);

        boolean isDpsPromiseActive = false;
        for (OrderShipmentData shipment : orderData.getCustomerShipments()) {
            if (shipment.includesOrderItem(customerOrderItemId)) {
                isDpsPromiseActive = shipment.isDpsPromiseActive();
            }
        }

        return DeliveryPromise.builder()
                   .withCustomerOrderId(orderData.getOrderId())
                   .withCustomerOrderItemId(orderItemData.getCustomerOrderItemId())
                   .withPromiseQuantity(orderItemData.getQuantity())
                   .withFulfillmentSvcSubclassId("2")
                   .withPromiseDataSource("SLAM")
                   .withPromiseEffectiveDate(promiseEffectiveDate)
                   .withIsActive(isDpsPromiseActive)
                   .withPromiseLatestShipDate(promiseLatestShipDate)
                   .withPromiseProvidedBy("DPS")
                   .withAsin(orderItemData.getAsin())
                   .withPlanQualityTypeCode("Normal")
                   .withPromiseLatestArrivalDate(promiseLatestArrivalDate)
                   .build();
    }
}
