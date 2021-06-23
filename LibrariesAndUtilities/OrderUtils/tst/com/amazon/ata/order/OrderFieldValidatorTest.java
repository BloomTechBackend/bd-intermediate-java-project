package com.amazon.ata.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderFieldValidatorTest {
    private OrderFieldValidator orderFieldValidator;

    @BeforeEach
    private void setup() {
        orderFieldValidator = new OrderFieldValidator();
    }

    // order ID

    @Test
    public void isValidOrderId_nullReturnsFalse() {
        // GIVEN
        // WHEN
        boolean isValid = orderFieldValidator.isValidOrderId(null);

        // THEN
        assertFalse(isValid, "Expected isValidOrderId(null) to return false");
    }

    @Test
    public void isValidOrderId_emptyStringReturnsFalse() {
        // GIVEN
        String invalidOrderId = "";

        // WHEN
        boolean isValid = orderFieldValidator.isValidOrderId(invalidOrderId);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidOrderId('%s') to return false, but returned true", invalidOrderId));
    }

    @Test
    public void isValidOrderId_shortFirstSegmentReturnsFalse() {
        // GIVEN
        String invalidOrderId = "11-2222222-3838384";

        // WHEN
        boolean isValid = orderFieldValidator.isValidOrderId(invalidOrderId);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidOrderId('%s') to return false, but returned true", invalidOrderId));
    }

    @Test
    public void isValidOrderId_shortSecondSegmentReturnsFalse() {
        // GIVEN
        String invalidOrderId = "111-222222-3838384";

        // WHEN
        boolean isValid = orderFieldValidator.isValidOrderId(invalidOrderId);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidOrderId('%s') to return false, but returned true", invalidOrderId));
    }

    @Test
    public void isValidOrderId_shortThirdSegmentReturnsFalse() {
        // GIVEN
        String invalidOrderId = "113-2222222-383838";

        // WHEN
        boolean isValid = orderFieldValidator.isValidOrderId(invalidOrderId);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidOrderId('%s') to return false, but returned true", invalidOrderId));
    }


    @Test
    public void isValidOrderId_longFirstSegmentReturnsFalse() {
        // GIVEN
        String invalidOrderId = "1131-2222222-3838384";

        // WHEN
        boolean isValid = orderFieldValidator.isValidOrderId(invalidOrderId);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidOrderId('%s') to return false, but returned true", invalidOrderId));
    }

    @Test
    public void isValidOrderId_longSecondSegmentReturnsFalse() {
        // GIVEN
        String invalidOrderId = "113-22222221-3838384";

        // WHEN
        boolean isValid = orderFieldValidator.isValidOrderId(invalidOrderId);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidOrderId('%s') to return false, but returned true", invalidOrderId));
    }

    @Test
    public void isValidOrderId_longThirdSegmentReturnsFalse() {
        // GIVEN
        String invalidOrderId = "113-2222222-38383841";

        // WHEN
        boolean isValid = orderFieldValidator.isValidOrderId(invalidOrderId);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidOrderId('%s') to return false, but returned true", invalidOrderId));
    }

    @Test
    public void isValidOrderId_missingHyphensReturnsFalse() {
        // GIVEN
        String invalidOrderId = "11322222223838384";

        // WHEN
        boolean isValid = orderFieldValidator.isValidOrderId(invalidOrderId);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidOrderId('%s') to return false, but returned true", invalidOrderId));
    }

    @Test
    public void isValidOrderId_misplacedHyphensReturnsFalse() {
        // GIVEN
        String invalidOrderId = "1132222-222-3838384";

        // WHEN
        boolean isValid = orderFieldValidator.isValidOrderId(invalidOrderId);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidOrderId('%s') to return false, but returned true", invalidOrderId));
    }

    @Test
    public void isValidOrderId_nonNumericStringReturnsFalse() {
        // GIVEN
        String invalidOrderId = "113-2222222-3838AAA";

        // WHEN
        boolean isValid = orderFieldValidator.isValidOrderId(invalidOrderId);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidOrderId('%s') to return false, but returned true", invalidOrderId));
    }

    @Test
    public void isValidOrderId_validFormatReturnsTrue() {
        // GIVEN
        String validOrderId = "113-2222222-3838384";

        // WHEN
        boolean isValid = orderFieldValidator.isValidOrderId(validOrderId);

        // THEN
        assertTrue(isValid,
                    String.format("Expected isValidOrderId('%s') to return true, but returned false", validOrderId));
    }

    // condition code

    @Test
    public void isValidCondition_returnsFalseOnNegative() {
        // GIVEN
        int invalidCode = -1;

        // WHEN
        boolean isValid = orderFieldValidator.isValidCondition(invalidCode);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidCondition(%d) to return false, but returned true", invalidCode));
    }

    @Test
    public void isValidCondition_returnsFalseOnLargeNumber() {
        // GIVEN
        int invalidCode = 9847664;

        // WHEN
        boolean isValid = orderFieldValidator.isValidCondition(invalidCode);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidCondition(%d) to return false, but returned true", invalidCode));
    }

    @Test
    public void isValidCondition_returnsFalseOnSeven() {
        // GIVEN
        int invalidCode = 7;

        // WHEN
        boolean isValid = orderFieldValidator.isValidCondition(invalidCode);

        // THEN
        assertFalse(isValid,
                    String.format("Expected isValidCondition(%d) to return false, but returned true", invalidCode));
    }

    @Test
    public void isValidCondition_returnsTrueBetweenZeroAndSix() {
        // GIVEN
        int[] validCodes = {0, 1, 2, 3, 4, 5, 6};

        // WHEN + THEN
        for (int validCode : validCodes) {
            assertTrue(orderFieldValidator.isValidCondition(validCode),
                        String.format("Expected isValidCondition(%d) to return true, but returned false", validCode));
        }
    }
}
