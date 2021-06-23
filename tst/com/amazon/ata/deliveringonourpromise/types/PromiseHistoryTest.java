package com.amazon.ata.deliveringonourpromise.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Unit Test PromiseHistory
 */
public class PromiseHistoryTest {

    private Promise mockPromise;
    private Order mockOrder;

    @BeforeEach
    private void createLogic() {
        mockPromise = mock(Promise.class);
        mockOrder = mock(Order.class);
    }

    @Test
    public void constructPromiseHistory_nullOrder_nonNullHistory() {
        // GIVEN
        Order order = null;

        // WHEN
        PromiseHistory history = new PromiseHistory(order);

        // THEN - no exception thrown
        assertNotNull(history);
    }


    @Test
    public void addPromise_orderIsNull_throwsException() {
        // GIVEN
        Order order = null;
        PromiseHistory history = new PromiseHistory(order);

        // WHEN & THEN
        Assertions.assertThrows(IllegalArgumentException.class, () -> history.addPromise(mockPromise),
                                "Should not be able to add a promise to a promise history object with a null order.");

    }

    @Test
    public void addPromise_nullPromise_throwsException() {
        // GIVEN
        PromiseHistory history = new PromiseHistory(mockOrder);

        // WHEN & THEN
        Assertions.assertThrows(IllegalArgumentException.class, () -> history.addPromise(null),
                                "Should not be able to add a null promise to a promise history.");

    }

    @Test
    public void addPromise_addedPromise_isPersistedInPromiseList() {
        // GIVEN
        PromiseHistory history = new PromiseHistory(mockOrder);

        // WHEN
        history.addPromise(mockPromise);

        // THEN
        assertTrue(history.getPromises().contains(mockPromise));
    }
}
