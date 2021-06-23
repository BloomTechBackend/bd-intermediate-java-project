package com.amazon.ata.deliveringonourpromise.types;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PromiseTest {

    @Test
    public void setDeliveryDate_whenSet_returnsDeliveryDate() {
        // GIVEN
        ZonedDateTime deliveryDate = ZonedDateTime.now();
        Promise promise = Promise.builder()
            .withDeliveryDate(null)
            .build();
        assertNull(promise.getDeliveryDate());

        // WHEN
        promise.setDeliveryDate(deliveryDate);

        // THEN
        assertEquals(deliveryDate, promise.getDeliveryDate());
    }
}
