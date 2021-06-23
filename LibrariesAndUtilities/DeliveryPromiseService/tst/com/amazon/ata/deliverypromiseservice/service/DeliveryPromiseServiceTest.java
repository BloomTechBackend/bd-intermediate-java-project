package com.amazon.ata.deliverypromiseservice.service;

import com.amazon.ata.deliveringonourpromise.data.OrderData;
import com.amazon.ata.deliveringonourpromise.data.OrderDatastore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeliveryPromiseServiceTest {
    private DeliveryPromiseService dps;
    private String orderId;
    private String orderItemId;

    @BeforeEach
    private void setup() {
        OrderDatastore orderDatastore = OrderDatastore.getDatastore();
        dps = new DeliveryPromiseService(orderDatastore);
        orderId = "111-7497023-2969385";
        OrderData orderData = orderDatastore.getOrderData(orderId);
        orderItemId = orderData.getCustomerOrderItemList().get(0).getCustomerOrderItemId();
    }

    @Test
    public void getDeliveryPromise_returnsNullOnNonexistentOrderItemId() {
        // GIVEN - too short to be a valid ID
        orderItemId = "1";

        // WHEN
        DeliveryPromise deliveryPromise = dps.getDeliveryPromise(orderItemId);

        // THEN
        assertNull(deliveryPromise);
    }

    @Test
    public void getDeliveryPromise_hasFulfillmentSvcSubclassId() {
        // WHEN
        DeliveryPromise deliveryPromise = dps.getDeliveryPromise(orderItemId);

        // THEN
        assertNotNull(deliveryPromise.getFulfillmentSvcSubclassId());
    }

    @Test
    public void getDeliveryPromise_hasCorrectCustomerOrderItemId() {
        // WHEN
        DeliveryPromise deliveryPromise = dps.getDeliveryPromise(orderItemId);

        // THEN
        assertEquals(orderItemId, deliveryPromise.getCustomerOrderItemId());
    }

    @Test
    public void getDeliveryPromise_hasCorrectCustomerOrderId() {
        // WHEN
        DeliveryPromise deliveryPromise = dps.getDeliveryPromise(orderItemId);

        // THEN
        assertEquals(orderId, deliveryPromise.getCustomerOrderId());
    }

    @Test
    public void getDeliveryPromise_hasPromiseDataSource() {
        // WHEN
        DeliveryPromise deliveryPromise = dps.getDeliveryPromise(orderItemId);

        // THEN
        assertNotNull(deliveryPromise.getPromiseDataSource());
    }

    @Test
    public void getDeliveryPromise_hasPromiseEffectiveDate() {
        // WHEN
        DeliveryPromise deliveryPromise = dps.getDeliveryPromise(orderItemId);

        // THEN
        assertNotNull(deliveryPromise.getPromiseEffectiveDate());
    }

    @Test
    public void getDeliveryPromise_hasPromiseLatestShipDate() {
        // WHEN
        DeliveryPromise deliveryPromise = dps.getDeliveryPromise(orderItemId);

        // THEN
        assertNotNull(deliveryPromise.getPromiseLatestShipDate());
    }

    @Test
    public void getDeliveryPromise_hasPromiseProvidedBy() {
        // WHEN
        DeliveryPromise deliveryPromise = dps.getDeliveryPromise(orderItemId);

        // THEN
        assertNotNull(deliveryPromise.getPromiseProvidedBy());
    }

    @Test
    public void getDeliveryPromise_hasAsin() {
        // WHEN
        DeliveryPromise deliveryPromise = dps.getDeliveryPromise(orderItemId);

        // THEN
        assertNotNull(deliveryPromise.getAsin());
    }

    @Test
    public void getDeliveryPromise_hasPlanQualityTypeCode() {
        // WHEN
        DeliveryPromise deliveryPromise = dps.getDeliveryPromise(orderItemId);

        // THEN
        assertNotNull(deliveryPromise.getAsin());
    }

    @Test
    public void getDeliveryPromise_haspromiseLatestArrivalDate() {
        // WHEN
        DeliveryPromise deliveryPromise = dps.getDeliveryPromise(orderItemId);

        // THEN
        assertNotNull(deliveryPromise.getPromiseLatestArrivalDate());
    }

}
