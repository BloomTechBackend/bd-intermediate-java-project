package com.amazon.ata.ordermanipulationauthority;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the possible states that an order can be in.
 */
public enum OrderCondition {
    PENDING(0, "Pending"),
    CONFIRMED(1, "Confirmed"),
    DECLINED(2, "Declined"),
    AUTHORIZED(3, "Authorized"),
    CLOSED(4, "Closed"),
    SHIPMENT_DECLINED(5, "Shipment Declined"),
    CANCELLED(6, "Cancelled");

    private static Map<Integer, OrderCondition> codeMappings = new HashMap<>();

    private int conditionCode;
    private String conditionDescription;

    /**
     * Builds an order condition from code and human-readable description.
     *
     * @param conditionCode numeric code representing the condition
     * @param conditionDescription human-readable description of the condition
     */
    OrderCondition(int conditionCode, String conditionDescription) {
        this.conditionCode = conditionCode;
        this.conditionDescription = conditionDescription;
    }

    /**
     * Returns the OrderCondition corresponding to the given code.
     * @param code the numeric code for the OrderCondition to look up
     * @return the OrderCondition corresponding to the given code if valid, null otherwise.
     */
    public static OrderCondition fromCode(int code) {
        if (codeMappings.isEmpty()) {
            for (OrderCondition condition : OrderCondition.values()) {
                codeMappings.put(condition.getConditionCode(), condition);
            }
        }

        return codeMappings.get(code);
    }

    public int getConditionCode() {
        return conditionCode;
    }

    public String getConditionDescription() {
        return conditionDescription;
    }

    /**
     * return string representation of the order condition.
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%d - %s", getConditionCode(), getConditionDescription());
    }
}
