package com.amazon.ata.deliveringonourpromise.test.integration;

import com.amazon.ata.deliveringonourpromise.App;
import com.amazon.ata.deliveringonourpromise.promisehistoryservice.PromiseHistoryClient;
import com.amazon.ata.deliveringonourpromise.types.PromiseHistory;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the GetPromiseHistoryByOrderId API, intended for
 * regression testing purposes. These should all pass in the base project
 * and still pass as you progress through the project tasks.
 */
public class GetPromiseHistoryByOrderIdIntegrationTests {
    private PromiseHistoryClient client = App.getPromiseHistoryClient();

    @Test
    public void singleItemOrder() {
        // GIVEN
        String orderId = "111-7497023-2960775";

        // WHEN
        PromiseHistory history = client.getPromiseHistoryByOrderId(orderId);

        // THEN
        assertThat(history).isNotNull();
    }
}
