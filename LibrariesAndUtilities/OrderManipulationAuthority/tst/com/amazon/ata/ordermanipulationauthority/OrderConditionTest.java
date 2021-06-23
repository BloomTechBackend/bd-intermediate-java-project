package com.amazon.ata.ordermanipulationauthority;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderConditionTest {

    @Test
    public void fromCode() {
        // WHEN + THEN
        assertEquals(OrderCondition.fromCode(0), OrderCondition.PENDING);
        assertEquals(OrderCondition.fromCode(1), OrderCondition.CONFIRMED);
        assertEquals(OrderCondition.fromCode(2), OrderCondition.DECLINED);
        assertEquals(OrderCondition.fromCode(3), OrderCondition.AUTHORIZED);
        assertEquals(OrderCondition.fromCode(4), OrderCondition.CLOSED);
        assertEquals(OrderCondition.fromCode(5), OrderCondition.SHIPMENT_DECLINED);
        assertEquals(OrderCondition.fromCode(6), OrderCondition.CANCELLED);
    }

    @Test
    public void fromCode_nullOnUnrecognizedCode() {
        // WHEN + THEN
        assertNull(OrderCondition.fromCode(-1));
        assertNull(OrderCondition.fromCode(7));
    }
}
