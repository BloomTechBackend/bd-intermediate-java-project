package com.amazon.ata.orderfulfillmentservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderPromiseTest {
    private OrderPromise orderPromise;

    @BeforeEach
    private void setup() {
        orderPromise = OrderPromise.builder()
                           .withCustomerOrderId("113-4938334-2196208")
                           .withCustomerOrderItemId("14418401775681")
                           .withPromiseDataSource("SLAM")
                           .withPromiseEffectiveDate(ZonedDateTime.now())
                           .withPromiseLatestArrivalDate(ZonedDateTime.now().plusDays(2))
                           .withPromiseProvidedBy("OFS")
                           .withPromiseQuantity(9)
                           .withAsin("B07C9JYF2W")
                           .withFulfillmentSvcSubclassId("2")
                           .withPromiseLatestShipDate(ZonedDateTime.now().plusHours(6))
                           .withIsActive(true)
                           .withPlanQualityTypeCode("Normal")
                           .build();
    }

    @Test
    public void toString_containsExpectedFields() {
        // WHEN
        String stringifiedOrderPromise = orderPromise.toString();

        // THEN
        assertSubstringMatch("customerOrderId", stringifiedOrderPromise);
        assertSubstringMatch("customerOrderItemId", stringifiedOrderPromise);
        assertSubstringMatch("promiseDataSource", stringifiedOrderPromise);
        assertSubstringMatch("promiseEffectiveDate", stringifiedOrderPromise);
        assertSubstringMatch("promiseLatestArrivalDate", stringifiedOrderPromise);
        assertSubstringMatch("promiseProvidedBy", stringifiedOrderPromise);
        assertSubstringMatch("promiseQuantity", stringifiedOrderPromise);
        assertSubstringMatch("asin", stringifiedOrderPromise);
        assertSubstringMatch("promiseLatestShipDate", stringifiedOrderPromise);
        assertSubstringMatch("isActive", stringifiedOrderPromise);
        assertSubstringMatch("planQualityTypeCode", stringifiedOrderPromise);
    }

    @Test
    public void toString_containsExpectedValues() {
        // WHEN
        String stringifiedOrderPromise = orderPromise.toString();

        // THEN
        assertSubstringMatch(orderPromise.getCustomerOrderId(), stringifiedOrderPromise);
        assertSubstringMatch(orderPromise.getCustomerOrderItemId(), stringifiedOrderPromise);
        assertSubstringMatch(orderPromise.getPromiseDataSource(), stringifiedOrderPromise);
        assertSubstringMatch(orderPromise.getPromiseEffectiveDate()
                                 .toLocalDateTime().toString(),
                             stringifiedOrderPromise);
        assertSubstringMatch(orderPromise.getPromiseLatestArrivalDate()
                                 .toLocalDateTime().toString(),
                             stringifiedOrderPromise);
        assertSubstringMatch(orderPromise.getPromiseProvidedBy(), stringifiedOrderPromise);
        assertSubstringMatch(String.valueOf(orderPromise.getPromiseQuantity()), stringifiedOrderPromise);
        assertSubstringMatch(orderPromise.getAsin(), stringifiedOrderPromise);
        assertSubstringMatch(orderPromise.getPromiseLatestShipDate()
                                 .toLocalDateTime().toString(),
                             stringifiedOrderPromise);
        assertSubstringMatch(String.valueOf(orderPromise.isActive()), stringifiedOrderPromise);
        assertSubstringMatch(orderPromise.getPlanQualityTypeCode(), stringifiedOrderPromise);
    }

    private void assertSubstringMatch(String pattern, String result) {
        String matchPattern = new StringBuilder(".*").append(pattern).append(".*").toString();
        assertTrue(Pattern.matches(matchPattern, result),
                   String.format("Expected to find substring, '%s', in String result '%s' but did not",
                                 pattern,
                                 result
                   )
        );
    }
}
