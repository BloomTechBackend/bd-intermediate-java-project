package com.amazon.ata.deliveringonourpromise;

import com.amazon.ata.deliveringonourpromise.promisehistoryservice.PromiseHistoryClient;
import com.amazon.ata.deliveringonourpromise.types.Order;
import com.amazon.ata.deliveringonourpromise.types.OrderItem;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.deliveringonourpromise.types.PromiseHistory;
import com.amazon.ata.input.console.ATAUserHandler;
import com.amazon.ata.ordermanipulationauthority.OrderCondition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShellTest {

    private PromiseHistoryClient mockPromiseHistoryClient;
    private ATAUserHandler mockUserHandler;
    private Shell shell;

    @BeforeEach
    private void createShell() {
        mockPromiseHistoryClient = mock(PromiseHistoryClient.class);
        mockUserHandler = mock(ATAUserHandler.class);
        shell = new Shell(mockPromiseHistoryClient, mockUserHandler);
    }

    @Test
    public void handleUserRequest_orderWithNullFields_doesntRaiseException() {
        // GIVEN
        String orderId = "111-7497023-2960775";
        Order order = Order.builder()
                          .withOrderId(orderId)
                          .withCustomerId("12345")
                          .build();
        PromiseHistory promiseHistory = new PromiseHistory(order);
        when(mockUserHandler.getString(anyString(), anyString())).thenReturn(orderId);
        when(mockPromiseHistoryClient.getPromiseHistoryByOrderId(anyString())).thenReturn(promiseHistory);

        // WHEN
        String result = shell.handleUserRequest();

        // THEN - no exception thrown
        assertNotNull(result);
    }

    @Test
    public void handleUserRequest_orderDoesNotExist_doesntRaiseException() {
        // GIVEN
        String unknownOrderId = "111";
        Order order = null;
        PromiseHistory promiseHistory = new PromiseHistory(order);
        when(mockUserHandler.getString(anyString(), anyString())).thenReturn(unknownOrderId);
        when(mockPromiseHistoryClient.getPromiseHistoryByOrderId(anyString())).thenReturn(promiseHistory);

        // WHEN
        String result = shell.handleUserRequest();

        assertEquals(result, String.format("Unable to find any order data for orderId: %s. Please check your " +
                                           "order id and try again.", unknownOrderId));
    }


    @Test
    void handleUserRequest_withExistingOrder_containsCorrectOrderData() {
        // GIVEN
        String orderId = "111-7497023-2960775";
        Order order = Order.builder()
                          .withOrderId(orderId)
                          .withCustomerId("12345")
                          .withCondition(OrderCondition.CLOSED)
                          .withMarketplaceId("1")
                          .withOrderDate(ZonedDateTime.now().minusDays(1))
                          .withShipOption("second")
                          .build();
        PromiseHistory promiseHistory = new PromiseHistory(order);

        when(mockUserHandler.getString(anyString(), anyString())).thenReturn(orderId);
        when(mockPromiseHistoryClient.getPromiseHistoryByOrderId(anyString())).thenReturn(promiseHistory);

        // WHEN
        String result = shell.handleUserRequest();

        // THEN
        assertOrderMatch(order, result);
    }

    @Test
    void handleUserRequest_withPromiseWithNullFields_RendersPromiseRow() {
        // GIVEN
        String orderId = "111-7497023-2960775";
        String orderItemId = "1234567890123";
        OrderItem orderItem = OrderItem.builder().withOrderId(orderId).build();
        Order order = Order.builder()
                          .withOrderId(orderId)
                          .withCustomerOrderItemList(Arrays.asList(orderItem))
                          .withCustomerId("12345")
                          .withCondition(OrderCondition.CLOSED)
                          .withMarketplaceId("1")
                          .withOrderDate(ZonedDateTime.now().minusDays(1))
                          .withShipOption("second")
                          .build();
        Promise promise = Promise.builder().withCustomerOrderItemId(orderItemId).build();
        PromiseHistory promiseHistory = new PromiseHistory(order);
        promiseHistory.addPromise(promise);
        when(mockUserHandler.getString(anyString(), anyString())).thenReturn(order.getOrderId());
        when(mockPromiseHistoryClient.getPromiseHistoryByOrderId(any())).thenReturn(promiseHistory);

        // WHEN
        String result = shell.handleUserRequest();

        // THEN - should have a promise row, and should only see order item ID (plus not active, zero confidence score)
        String[] rowEntries = {"", "", orderItemId, "N", "", "", "", ""};
        assertRowMatch(rowEntries, result);
    }

    @Test
    void handleUserRequest_withExistingOrder_containsCorrectPromiseHistoryData() {
        // GIVEN
        String orderId = "111-7497023-2960775";
        Order order = Order.builder()
                          .withOrderId(orderId)
                          .withCustomerId("12345")
                          .withCondition(OrderCondition.CLOSED)
                          .withMarketplaceId("1")
                          .withOrderDate(ZonedDateTime.now().minusDays(1))
                          .withShipOption("second")
                          .build();

        Promise promise1 = Promise.builder()
                               .withPromiseLatestArrivalDate(ZonedDateTime.now().plusDays(2))
                               .withCustomerOrderItemId("20655079937481")
                               .withPromiseEffectiveDate(ZonedDateTime.now().minusDays(1))
                               .withIsActive(false)
                               .withPromiseLatestShipDate(ZonedDateTime.now().plusDays(1))
                               .withPromiseProvidedBy("DPS")
                               .withAsin("B07C9JYF2W")
                               .withDeliveryDate(ZonedDateTime.now())
                               .build();

        Promise promise2 = Promise.builder()
                               .withPromiseLatestArrivalDate(ZonedDateTime.now().plusDays(3))
                               .withCustomerOrderItemId("20655079937499")
                               .withPromiseEffectiveDate(ZonedDateTime.now().minusDays(5))
                               .withIsActive(true)
                               .withPromiseLatestShipDate(ZonedDateTime.now().plusDays(2))
                               .withPromiseProvidedBy("DPS")
                               .withAsin("B0019H32G2")
                               .withDeliveryDate(ZonedDateTime.now().minusHours(1))
                               .build();

        PromiseHistory promiseHistory = new PromiseHistory(order);
        promiseHistory.addPromise(promise1);
        promiseHistory.addPromise(promise2);

        when(mockUserHandler.getString(anyString(), anyString())).thenReturn(order.getOrderId());
        when(mockPromiseHistoryClient.getPromiseHistoryByOrderId(any())).thenReturn(promiseHistory);

        // WHEN
        String result = shell.handleUserRequest();

        // THEN
        assertPromiseMatch(promise1, order, result);
        assertPromiseMatch(promise2, order, result);
    }

    @Test
    void userHasAnotherRequest_responseIsY_returnsTrue() {
        // GIVEN
        when(mockUserHandler.getString(any(), anyString(), anyString())).thenReturn("y").thenReturn("Y");

        // WHEN
        boolean lowerYResponse = shell.userHasAnotherRequest();
        boolean upperYResponse = shell.userHasAnotherRequest();

        // THEN
        assertTrue(lowerYResponse, "'y' should have resulted in true");
        assertTrue(upperYResponse, "'Y' should have resulted in true");
    }

    @Test
    void userHasAnotherRequest_responseIsN_returnsFalse() {
        // GIVEN
        when(mockUserHandler.getString(any(), anyString(), anyString())).thenReturn("n").thenReturn("N");

        // WHEN
        boolean lowerNResponse = shell.userHasAnotherRequest();
        boolean upperNResponse = shell.userHasAnotherRequest();

        // THEN
        assertFalse(lowerNResponse, "'n' should have resulted in false");
        assertFalse(upperNResponse, "'N' should have resulted in false");
    }

    @Test
    void main_withShowFixtures_showFixtureTable() {
        InputStream originalSystemIn = System.in;

        try {
            // GIVEN
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            String[] args = {Shell.SHOW_FIXTURES_FLAG};

            String consoleInput = "n";
            ByteArrayInputStream inContent = new ByteArrayInputStream(consoleInput.getBytes());
            System.setIn(inContent);

            // WHEN
            Shell.main(args);

            // THEN
            String result = outContent.toString();
            String[] headers = {"ORDER ID"};
            assertRowMatch(headers, result);
        } finally {
            System.setIn(originalSystemIn);
        }
    }


    private void assertOrderMatch(Order order, String result) {
        String[] expectedFields = {
            order.getOrderDate().toLocalDateTime().toString(),
            order.getOrderId(),
            order.getMarketplaceId(),
            order.getOrderDate().getZone().toString(),
            order.getCondition().toString(),
            order.getShipOption(),
            order.getCustomerId()
        };

        assertRowMatch(expectedFields, result);
    }

    private void assertPromiseMatch(Promise promise, Order order, String result) {
        String[] expectedFields = {
            promise.getPromiseEffectiveDate().toLocalDateTime().toString(),
            promise.getAsin(),
            promise.getCustomerOrderItemId(),
            promise.isActive() ? "Y" : "N",
            promise.getPromiseLatestShipDate().toLocalDateTime().toString(),
            promise.getPromiseLatestArrivalDate().toLocalDateTime().toString(),
            promise.getDeliveryDate().toLocalDateTime().toString(),
            promise.getPromiseProvidedBy()
        };

        assertRowMatch(expectedFields, result);
    }

    // see if there's a row with the given fields in the provided result string.
    private void assertRowMatch(String[] fields, String result) {
        // not sure why needed to do .* at beginning/end of the regex...it always failed when trying to do
        // partial/one-line match on the full output.

        String fieldMatch = "\\| %s\\s*";
        String matchPrefix = ".*";
        String matchSuffix = "\\|.*";

        StringBuilder builder = new StringBuilder(matchPrefix);
        for (int i = 0; i < fields.length; i++) {
            builder.append(fieldMatch);
        }
        builder.append(matchSuffix);
        String regex = String.format(builder.toString(), (Object[]) fields);
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);

        assertTrue(pattern.matcher(result).matches(),
                   "Expected result to match regex " + pattern + "result was: " + result);
    }

    // FIXME: Adding some required tests for testing purposes
    @Test
    void ARequiredTest() {
        assertTrue(true);
    }

    @Test
    void AnotherRequiredTest() {
        assertEquals(3.14, 3.14);
    }

    @Test
    void YetAnotherRequiredTest() {
        assertTrue(true);
    }

    // FIXME: Need a test that fails
    // @Test
    void AFailedTest() {
        assertTrue(false);
    }
}
