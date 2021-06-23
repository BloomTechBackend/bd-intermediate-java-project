package com.amazon.ata.deliveringonourpromise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppTest {

    @Test
    public void getPromiseHistoryClient_always_createsPromiseHistoryClient() {
        // WHEN + THEN
        assertNotNull(App.getPromiseHistoryClient());
    }
}
