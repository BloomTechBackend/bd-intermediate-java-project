package com.amazon.ata.deliveringonourpromise.types;

import java.util.ArrayList;
import java.util.List;

/**
 * Data object containing the order details as well as the set of promises for the given order. If the
 * order does not exist it will be null, and the PromiseHistory will contain no promises.
 */
public class PromiseHistory {
    private Order order;
    private List<Promise> promises = new ArrayList<>();

    /**
     * Constructs a new promise history with the given order. A null order value is allowed here.
     * If the order is null, no promises may be added to the history.
     *
     * @param order the order these promises correspond to
     */
    public PromiseHistory(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    /**
     * Adds a Promise to the promise history.
     *
     * @param newPromise the next promise to add to the history's promises
     */
    public void addPromise(Promise newPromise) {
        if (null == newPromise) {
            throw new IllegalArgumentException("PromiseHistory cannot accept null Promises");
        }

        if (order == null) {
            throw new IllegalArgumentException("Promises cannot be added for a null order.");
        }
        promises.add(newPromise);
    }

    /**
     * Returns the promise history's promises.
     *
     * @return the promises
     */
    public List<Promise> getPromises() {
        return new ArrayList<>(promises);
    }
}
