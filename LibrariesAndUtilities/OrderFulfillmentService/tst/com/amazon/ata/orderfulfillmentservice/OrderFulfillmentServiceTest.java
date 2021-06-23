package com.amazon.ata.orderfulfillmentservice;

import com.amazon.ata.deliveringonourpromise.data.OrderData;
import com.amazon.ata.deliveringonourpromise.data.OrderDatastore;
import com.amazon.ata.deliveringonourpromise.data.OrderItemData;
import com.amazon.ata.deliveringonourpromise.data.OrderShipmentData;
import com.amazon.ata.deliverypromiseservice.service.DeliveryPromise;
import com.amazon.ata.deliverypromiseservice.service.DeliveryPromiseService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class OrderFulfillmentServiceTest {
    private OrderFulfillmentService ofs;
    private DeliveryPromiseService mockDeliveryPromiseService;
    private OrderDatastore spyOrderDatastore;
    private DeliveryPromise deliveryPromiseServicePromise;

    // test data
    private String orderId;
    private String orderItemId;
    private OrderData orderData;
    private OrderItemData orderItemData;

    @BeforeEach
    private void setup() {
        spyOrderDatastore = spy(OrderDatastore.getDatastore());
        mockDeliveryPromiseService = mock(DeliveryPromiseService.class);
        ofs = new OrderFulfillmentService(spyOrderDatastore, mockDeliveryPromiseService);

        orderId = "111-7497023-2969386";
        orderData = spyOrderDatastore.getOrderData(orderId);
        orderItemData = orderData.getCustomerOrderItemList().get(0);
        orderItemId = orderItemData.getCustomerOrderItemId();

        deliveryPromiseServicePromise = stubDeliveryPromiseService();
    }

    @Test
    public void getOrderPromise_returnNullOnNonexistentOrderItemId() {
        // GIVEN - too short to be a valid ID
        orderItemId = "101";

        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertNull(orderPromise);
    }

    @Test
    public void getOrderPromise_hasCorrectCustomerOrderId() {
        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertEquals(orderId, orderPromise.getCustomerOrderId());
    }

    @Test
    public void getOrderPromise_hasCorrectCustomerOrderItemId() {
        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertEquals(orderItemId, orderPromise.getCustomerOrderItemId());
    }

    @Test
    public void getOrderPromise_hasFulfillmentSvcSubclassId() {
        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertNotNull(orderPromise.getFulfillmentSvcSubclassId());
    }

    @Test
    public void getOrderPromise_hasPromiseLatestArrivalDate() {
        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertNotNull(orderPromise.getPromiseLatestArrivalDate());
    }

    @Test
    public void getOrderPromise_hasCorrectPromiseQuantity() {
        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertEquals(orderItemData.getQuantity(), orderPromise.getPromiseQuantity());
    }

    @Test
    public void getOrderPromise_hasPromiseDataSource() {
        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertNotNull(orderPromise.getPromiseDataSource());
    }

    @Test
    public void getOrderPromise_hasPromiseEffectiveDate() {
        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertNotNull(orderPromise.getPromiseEffectiveDate());
    }

    @Test
    public void getOrderPromise_hasPlanQualityTypeCode() {
        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertNotNull(orderPromise.getPlanQualityTypeCode());
    }

    @Test
    public void getOrderPromise_isActiveIfItExists() {
        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertTrue(orderPromise.isActive());
    }

    @Test
    public void getOrderPromise_hasPromiseLatestShipDate() {
        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertNotNull(orderPromise.getPromiseLatestShipDate());
    }

    @Test
    public void getOrderPromise_hasOfsPromiseProvidedBy() {
        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertEquals("OFS", orderPromise.getPromiseProvidedBy());
    }

    @Test
    public void getOrderPromise_hasCorrectAsin() {
        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertEquals(orderItemData.getAsin(), orderPromise.getAsin());
    }

    @Test
    public void getOrderPromise_whenDpsAndOpsShouldAgree_hasSamePromiseDateAsDps() {
        // GIVEN
        forceOrderDataStoreToReturnDoDpsAndOfsPromisesAgree(true);
        ZonedDateTime promiseDate = orderData.getOrderDate().plusDays(3);
        forceDpsLatestArrivalDate(promiseDate);

        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertEquals(promiseDate,
                     orderPromise.getPromiseLatestArrivalDate(),
                     String.format("Expected DPS and OFS promise arrival dates to match, " +
                                   "but DPS's was %s and OFS's was %s",
                                   promiseDate,
                                   orderPromise.getPromiseLatestArrivalDate()
                     )
        );
    }

    @Test
    public void getOrderPromise_whenDpsAndOpsShouldDisagree_hasLaterPromiseThanDps() {
        // GIVEN
        forceOrderDataStoreToReturnDoDpsAndOfsPromisesAgree(false);
        ZonedDateTime deliveryPromiseDate = orderData.getOrderDate().plusDays(3);
        forceDpsLatestArrivalDate(deliveryPromiseDate);

        // WHEN
        OrderPromise orderPromise = ofs.getOrderPromise(orderItemId);

        // THEN
        assertTrue(deliveryPromiseDate.compareTo(orderPromise.getPromiseLatestArrivalDate()) < 0,
                   String.format("Expected OFS's latest arrival date to be after DPS's (%s), " +
                                 "but OFS's was %s",
                                 deliveryPromiseDate,
                                 orderPromise.getPromiseLatestArrivalDate()
                   ));
    }

    /* Sets up DPS to return a spy DeliveryPromise, on which we can modify behavior */
    private DeliveryPromise stubDeliveryPromiseService() {
        ZonedDateTime orderDate = orderData.getOrderDate();
        ZonedDateTime latestArrivalDate = orderDate.plusHours(53);
        ZonedDateTime latestShipDate = latestArrivalDate.minusHours(18);

        DeliveryPromise spyPromise = spy(DeliveryPromise.builder()
                                             .withCustomerOrderId(orderId)
                                             .withCustomerOrderItemId(orderItemId)
                                             .withPromiseLatestArrivalDate(orderData.getOrderDate().plusDays(2))
                                             .withPromiseLatestShipDate(latestShipDate)
                                             .build()
        );
        when(mockDeliveryPromiseService.getDeliveryPromise(orderItemId)).thenReturn(spyPromise);

        return spyPromise;
    }

    /*
     * Forces OrderDatastore to return OrderData that contains a ShipmentData in which we've
     * overwritten the doDpsAndOfsAgree state...
     */
    private void forceOrderDataStoreToReturnDoDpsAndOfsPromisesAgree(boolean doDpsAndOfsAgree) {
        OrderData orderDataWithDpsOfsAgreement = spy(this.orderData);
        for (OrderShipmentData shipment : this.orderData.getCustomerShipments()) {
            if (shipment.includesOrderItem(orderItemId)) {
                OrderShipmentData shipmentWithDpsOpsAgreeing = spy(shipment);
                when(shipmentWithDpsOpsAgreeing.doDpsAndOfsPromisesAgree()).thenReturn(doDpsAndOfsAgree);
                when(shipmentWithDpsOpsAgreeing.isOfsPromiseActive()).thenReturn(true);
                when(orderDataWithDpsOfsAgreement.getCustomerShipments())
                    .thenReturn(Arrays.asList(shipmentWithDpsOpsAgreeing));
                when(spyOrderDatastore.getOrderData(orderId)).thenReturn(orderDataWithDpsOfsAgreement);
            }
        }
    }

    /*
     * Forces the delivery promise returned by DPS to have specified promise (arrival) date.
     */
    private void forceDpsLatestArrivalDate(ZonedDateTime arrivalDate) {
        when(deliveryPromiseServicePromise.getPromiseLatestArrivalDate()).thenReturn(arrivalDate);
    }
}
