package com.amazon.ata.deliveringonourpromise.promisehistoryservice;

import com.amazon.ata.deliveringonourpromise.activity.GetPromiseHistoryByOrderIdActivity;
import com.amazon.ata.deliveringonourpromise.types.PromiseHistory;

/**
 * Client class for accessing the Promise History APIs.
 * Currently supports the GetPromiseHistoryByOrderId API.
 */
public class PromiseHistoryClient {

    private GetPromiseHistoryByOrderIdActivity getPromiseHistoryByOrderIdActivity;

    /**
     * Constructs a PromiseHistoryClient that will provide access the activity/ies needed
     * to access promise history data.
     *
     * @param getPromiseHistoryByOrderIdActivity the activity for
     *                                           handling GetPromiseHistoryByOrderId API
     */
    public PromiseHistoryClient(GetPromiseHistoryByOrderIdActivity getPromiseHistoryByOrderIdActivity) {
        this.getPromiseHistoryByOrderIdActivity = getPromiseHistoryByOrderIdActivity;
    }

    /**
     * Fetches the promise history for the given order ID. When the order is
     * found but no promises are found, the PromiseHistory will contain the
     * Order info, but the history will be empty. When the order is not found,
     * the Promise history will contain a null order and no history.
     *
     * @param orderId The order ID to retrieve the history for.
     * @return PromiseHistory for the order; may be empty if order not found
     *         or if no history is found.
     */
    public PromiseHistory getPromiseHistoryByOrderId(String orderId) {
        return getPromiseHistoryByOrderIdActivity.getPromiseHistoryByOrderId(orderId);
    }
}
