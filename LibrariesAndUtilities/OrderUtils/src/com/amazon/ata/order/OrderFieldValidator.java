package com.amazon.ata.order;

import java.util.regex.Pattern;

/**
 * Provides validation methods for Amazon customer order fields.
 */
public class OrderFieldValidator {
    private static final Pattern ORDER_ID_PATTERN = Pattern.compile("\\d{3}-\\d{7}-\\d{7}");
    private static final int MIN_CONDITION_CODE = 0;
    private static final int MAX_CONDITION_CODE = 6;

    /**
     * Indicates if a given String is a valid order ID or not.
     *
     * @param orderId the candidate String to validate whether it is valid order ID or not
     * @return true if orderId is a valid order ID; false otherwise.
     */
    public boolean isValidOrderId(String orderId) {
        if (null == orderId) {
            return false;
        }

        return ORDER_ID_PATTERN.matcher(orderId).matches();
    }

    /**
     * Indicates if a given condition code is valid or not.
     *
     * @param conditionCode the candidate condition integer to validate whether it is valid or not
     * @return true if the conditionCode is a valid code; false otherwise
     */
    public boolean isValidCondition(int conditionCode) {
        return conditionCode >= MIN_CONDITION_CODE && conditionCode <= MAX_CONDITION_CODE;
    }
}
