package com.amazon.ata.orderfulfillmentservice;

import com.amazon.ata.deliveringonourpromise.data.OrderData;
import com.amazon.ata.deliveringonourpromise.data.OrderDatastore;
import com.amazon.ata.deliveringonourpromise.data.OrderItemData;
import com.amazon.ata.deliveringonourpromise.data.OrderShipmentData;
import com.amazon.ata.deliverypromiseservice.service.DeliveryPromise;
import com.amazon.ata.deliverypromiseservice.service.DeliveryPromiseService;

import java.time.ZonedDateTime;

/**
 * The service providing information on orders that are routing to or have come through order fulfillment.
 * This order information includes customer promises.
 */
public class OrderFulfillmentService {
    private OrderDatastore orderDatastore;
    private DeliveryPromiseService deliveryPromiseService;

    /**
     * Constructs an OrderFulfillmentService instance, using the provided OrderDatastore.
     *
     * @param orderDatastore         the datastore to use for pulling Order details
     * @param deliveryPromiseService reference to the DeliveryPromiseService to fetch promises that can be kept
     */
    public OrderFulfillmentService(OrderDatastore orderDatastore, DeliveryPromiseService deliveryPromiseService) {
        this.orderDatastore = orderDatastore;
        this.deliveryPromiseService = deliveryPromiseService;
    }

    /**
     * Returns the OrderPromise for the given order item, if any such promise exists.
     *
     * @param customerOrderItemId The customer order item ID for the item being queried
     * @return an OrderPromise for the given order item, if such a promise exists; null, otherwise (including if the
     * order item ID does not exist)
     */
    public OrderPromise getOrderPromise(String customerOrderItemId) {
        OrderItemData orderItemData = orderDatastore.getOrderItemData(customerOrderItemId);
        if (null == orderItemData) {
            return null;
        }

        OrderData orderData = orderDatastore.getOrderData(orderItemData.getOrderId());
        if (null == orderData) {
            return null;
        }

        return buildOrderPromise(orderData, orderItemData);
    }

    /*
     * Build the OrderPromise for the given order item.
     *
     * Service mocking:
     * * In order to create the scenario of a delivery promise existing but no order promise existing,
     *   buildOrderPromise refers to the Order record to see if the OFS promise should exist or not. We wouldn't
     *   expect OFS to actually work this way (it would have created the promise itself or not, based on where
     *   the shipments are in the fulfillment pipeline).
     *
     * * Similarly, to create scenarios of the delivery and order promises agreeing/disagreeing,
     *   buildOrderPromise consults the Order record to see if they should have the same promise date.
     *   We wouldn't expect OFS to work in exactly this way (though it might be aware of the earlier DPS promise
     *   made and endeavor to honor it).
     */
    private OrderPromise buildOrderPromise(OrderData orderData, OrderItemData orderItemData) {
        String customerOrderItemId = orderItemData.getCustomerOrderItemId();
        if (!isOfsPromiseActiveForOrderItem(orderData, customerOrderItemId)) {
            return null;
        }

        ZonedDateTime orderDate = orderData.getOrderDate();
        ZonedDateTime promiseEffectiveDate = orderDate.plusHours(8);
        ZonedDateTime promiseLatestArrivalDate;
        ZonedDateTime promiseLatestShipDate;
        DeliveryPromise deliveryPromise = deliveryPromiseService.getDeliveryPromise(customerOrderItemId);

        // If the Order record indicates that DPS and OFS dates should match, maintain the DPS promises dates
        // Otherwise, compute later promise arrival dates for OFS.
        if (matchesDpsPromiseDate(orderData, customerOrderItemId)) {
            promiseLatestArrivalDate = deliveryPromise.getPromiseLatestArrivalDate();
            promiseLatestShipDate = deliveryPromise.getPromiseLatestShipDate();
        } else {
            promiseLatestArrivalDate = deliveryPromise.getPromiseLatestArrivalDate().plusDays(1).plusHours(2);
            promiseLatestShipDate = promiseLatestArrivalDate.minusHours(15);
        }

        return OrderPromise.builder()
                   .withCustomerOrderId(orderData.getOrderId())
                   .withCustomerOrderItemId(customerOrderItemId)
                   .withAsin(orderItemData.getAsin())
                   .withIsActive(isOfsPromiseActiveForOrderItem(orderData, customerOrderItemId))
                   .withPromiseQuantity(orderItemData.getQuantity())
                   .withPromiseEffectiveDate(promiseEffectiveDate)
                   .withPromiseLatestShipDate(promiseLatestShipDate)
                   .withPromiseLatestArrivalDate(promiseLatestArrivalDate)
                   .withFulfillmentSvcSubclassId("1")
                   .withPlanQualityTypeCode("Normal")
                   .withPromiseDataSource("SLAM")
                   .withPromiseProvidedBy("OFS")
                   .build();
    }

    /*
     * Consults the Order record to determine if OFS's promise (arrival) date should match DPS's.
     */
    private boolean matchesDpsPromiseDate(OrderData orderData, String customerOrderItemId) {
        for (OrderShipmentData shipment : orderData.getCustomerShipments()) {
            if (shipment.includesOrderItem(customerOrderItemId)) {
                return shipment.doDpsAndOfsPromisesAgree();
            }
        }

        return false;
    }

    /*
     * Consults the Order record to determine if there should be an OFS promise (true) or not (false).
     */
    private boolean isOfsPromiseActiveForOrderItem(OrderData orderData, String customerOrderItemId) {
        for (OrderShipmentData shipment : orderData.getCustomerShipments()) {
            if (shipment.includesOrderItem(customerOrderItemId)) {
                return shipment.isOfsPromiseActive();
            }
        }

        return false;
    }
}
