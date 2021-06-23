package com.amazon.ata.deliveringonourpromise.activity;

import com.amazon.ata.deliveringonourpromise.App;
import com.amazon.ata.deliveringonourpromise.dao.ReadOnlyDao;
import com.amazon.ata.deliveringonourpromise.types.Order;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.deliveringonourpromise.types.PromiseHistory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Tests GetPromiseHistoryByOrderIdActivity.
 */
public class GetPromiseHistoryByOrderIdActivityTest {
    private GetPromiseHistoryByOrderIdActivity activity;
    private ReadOnlyDao<String, Order> orderDao;
    private ReadOnlyDao<String, List<Promise>> promiseDao;

    // participants: @BeforeEach means this method is run before each test method is executed, often setting up data +
    // an instance of the class under test.
    @BeforeEach
    private void createActivity() {
        orderDao = App.getOrderDao();
        promiseDao = App.getPromiseDao();
        activity = new GetPromiseHistoryByOrderIdActivity(orderDao, promiseDao);
    }

    @Test
    public void getPromiseHistoryByOrderId_nullOrderId_isRejected() {
        // GIVEN
        String orderId = null;

        // WHEN + THEN
        // (participants: we'll learn what this is doing later in the course)
        assertThrows(IllegalArgumentException.class, () -> activity.getPromiseHistoryByOrderId(orderId));
    }

    @Test
    public void getPromiseHistoryByOrderId_orderWithDpsPromise_returnsDpsPromise() {
        // GIVEN - an order that hasn't shipped yet but should return a DPS promise
        String orderId = "900-3746401-0000001";

        // WHEN
        PromiseHistory history = activity.getPromiseHistoryByOrderId(orderId);

        // THEN
        boolean foundDpsPromise = false;
        for (Promise promise : history.getPromises()) {
            if (promise.getPromiseProvidedBy().equals("DPS")) {
                foundDpsPromise = true;
            }
        }
        assertTrue(foundDpsPromise,
                   String.format("Expected to find a DPS promise for order ID '%s', but promises were: %s",
                                 orderId,
                                 history.getPromises().toString()
                   )
        );
    }
}
