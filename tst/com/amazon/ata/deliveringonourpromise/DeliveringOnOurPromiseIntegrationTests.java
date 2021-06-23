package com.amazon.ata.deliveringonourpromise;

import com.amazon.ata.deliveringonourpromise.data.OrderData;
import com.amazon.ata.deliveringonourpromise.data.OrderDatastore;
import com.amazon.ata.deliveringonourpromise.data.OrderFixture;
import com.amazon.ata.deliveringonourpromise.data.OrderItemData;
import com.amazon.ata.deliveringonourpromise.data.OrderShipmentData;
import com.amazon.ata.input.console.ATAUserHandler;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Integration tests for the DeliveringOnOurPromise project, intended for
 * regression testing purposes. These should all pass in the base project
 * and still pass as you progress through the project tasks.
 *
 * Since these must pass the base project, we can't test for OFS promises,
 * multiple items, or confidence erasure. Instead, we will test all the
 * fixtures and ensure that they contain the expected DPS promise.
 *
 * Additionally, we'll test that the order and promise headers are correct.
 */
public class DeliveringOnOurPromiseIntegrationTests {
    private static final String ANY_DATE = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
    private static final String ANY_FIELD = "[^|]*";
    private static final String ANY_DIGITS = "\\d*";
    private static final String ANY_ACTIVE = "[YN]";

    private ATAUserHandler input = mock(ATAUserHandler.class);
    private Shell shell = new Shell(App.getPromiseHistoryClient(), input);

    @Test
    public void testOrderHeaderColumns() {
        // GIVEN
        final String orderId = "900-3746401-0000001";
        given(input.getString(any(String.class), any(String.class))).willReturn(orderId);
        final String expectedOrderHeader = regexForRowOfFields(new ExpectedOrderRow()
                .withExpectedOrderDate("ORDER DATE")
                .withExpectedOrderId("ORDER ID")
                .withExpectedMarketplace("MARKETPLACE")
                .withExpectedTimezone("TIMEZONE")
                .withExpectedCondition("CONDITION")
                .withExpectedShipOption("SHIP OPTION")
                .withExpectedCustomerId("CUSTOMER ID")
                .getFields());

        // WHEN
        final String table = shell.handleUserRequest();

        // THEN
        then(rowInTable(expectedOrderHeader, table))
                .as(String.format("Expected order header to be unmodified!%n%n  regex: %s%n  table: %s",
                        expectedOrderHeader, table))
                .isTrue();
    }

    @Test
    public void testPromiseHeaderColumns() {
        // GIVEN
        final String orderId = "900-3746401-0000001";
        given(input.getString(any(String.class), any(String.class))).willReturn(orderId);
        final String expectedPromiseHeader = regexForRowOfFields(new ExpectedPromiseRow()
                .withExpectedEffectiveDate("EFFECTIVE DATE")
                .withExpectedAsin("ASIN")
                .withExpectedItemId("ITEM ID")
                .withExpectedActive("ACTIVE")
                .withExpectedPromisedShipDate("PROMISED SHIP DATE")
                .withExpectedPromisedDeliveryDate("PROMISED DELIVERY DATE")
                .withExpectedDeliveryDate("DELIVERY DATE")
                .withExpectedProvidedBy("PROVIDED BY")
                .withExpectedConfidence("CONFIDENCE")
                .getFields());

        // WHEN
        final String table = shell.handleUserRequest();

        // THEN
        then(rowInTable(expectedPromiseHeader, table))
                .as(String.format("Expected promise header to be unmodified!%n%n  regex: %s%n  table: %s",
                        expectedPromiseHeader, table))
                .isTrue();
    }

    @Test(dataProvider = "fixtureFields")
    public void testFixturePromises(final String orderId, final List<String> promiseFields) {
        // GIVEN
        // The orderId of the fixture from the parameters above will produce the fields from the parameter
        given(input.getString(any(String.class), any(String.class))).willReturn(orderId);
        final String expectedPromise = regexForRowOfFields(promiseFields);

        // WHEN
        final String table = shell.handleUserRequest();

        // THEN
        then(rowInTable(expectedPromise, table))
                .as(String.format("Expected promise for fixture %s in table!%n%n  regex: %s%n  table: %s",
                        orderId, expectedPromise, table))
                .isTrue();
    }

    // Determine whether the row is in the table
    private boolean rowInTable(final String regex, String result) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);

        return pattern.matcher(result).find();
    }

    private String regexForRowOfFields(List<String> fields) {
        String fieldMatch = "\\| %s\\s*";
        String matchSuffix = "\\|.*";

        StringBuilder builder = new StringBuilder();
        for (final String field : fields) {
            builder.append(String.format(fieldMatch, field));
        }
        builder.append(matchSuffix);
        String regex = builder.toString();

        return regex;
    }

    @DataProvider(name = "fixtureFields")
    private Iterator<Object[]> fixtureFields() {
        // Return a list of IDs and promise row regex
        List<Object[]> parameters = new ArrayList<>();

        // Get all the fixtures
        final OrderDatastore fixtureDatastore = OrderDatastore.getDatastore();
        final Map<String, OrderFixture> orderFixtures = fixtureDatastore.getOrderFixtures();

        final List<OrderFixture> fixtures = orderFixtures.values().stream()
                .filter(f -> f.getOrderData() != null)
                .filter(f -> f.getOrderData().getCustomerOrderItemList().size() > 0)
                .collect(Collectors.toList());

        // We'll want to test every fixture that has at least one promise
        for (OrderFixture fixture : fixtures) {
            // Gather the data
            final OrderData orderData = fixture.getOrderData();
            final List<OrderItemData> items = orderData.getCustomerOrderItemList();
            final OrderItemData firstItem = items.get(0);
            final String firstItemId = firstItem.getCustomerOrderItemId();
            final OrderShipmentData firstItemShipment = orderData.getCustomerShipments().stream()
                    .filter(shipment -> shipment.includesOrderItem(firstItemId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(String.format(
                            "Order fixture %s has no shipment for item %s!", orderData.getOrderId(), firstItemId)));
            final String deliveryDateRegex = firstItemShipment.getDeliveryDate() != null ? ANY_DATE : ANY_FIELD;
            final String confidenceRegex =
                    firstItem.isConfidenceTracked() ? Integer.toString(firstItem.getConfidence()) : ANY_FIELD;

            // Create the row
            final List<String> fields = new ExpectedPromiseRow()
                    .withExpectedEffectiveDate(ANY_DATE)
                    .withExpectedAsin(firstItem.getAsin())
                    .withExpectedItemId(firstItemId)
                    .withExpectedActive(firstItemShipment.isDpsPromiseActive() ? "Y" : "N")
                    .withExpectedPromisedShipDate(ANY_DATE)
                    .withExpectedPromisedDeliveryDate(ANY_DATE)
                    .withExpectedDeliveryDate(deliveryDateRegex)
                    .withExpectedProvidedBy("DPS")
                    .withExpectedConfidence(confidenceRegex)
                    .getFields();

            Object[] idAndFields = {fixture.getOrderId(), fields};
            parameters.add(idAndFields);
        }

        return parameters.iterator();
    }

    private static class ExpectedPromiseRow {
        private Optional<String> expectedEffectiveDate = Optional.empty();
        private Optional<String> expectedAsin = Optional.empty();
        private Optional<String> expectedItemId = Optional.empty();
        private Optional<String> expectedActive = Optional.empty();
        private Optional<String> expectedPromisedShipDate = Optional.empty();
        private Optional<String> expectedPromisedDeliveryDate = Optional.empty();
        private Optional<String> expectedDeliveryDate = Optional.empty();
        private Optional<String> expectedProvidedBy = Optional.empty();
        private Optional<String> expectedConfidence = Optional.empty();

        public List<String> getFields() {
            List<String> fields = new ArrayList<>(10);
            fields.add(expectedEffectiveDate.orElse(ANY_FIELD));
            fields.add(expectedAsin.orElse(ANY_FIELD));
            fields.add(expectedItemId.orElse(ANY_DIGITS));
            fields.add(expectedActive.orElse(ANY_ACTIVE));
            fields.add(expectedPromisedShipDate.orElse(ANY_FIELD));
            fields.add(expectedPromisedDeliveryDate.orElse(ANY_FIELD));
            fields.add(expectedDeliveryDate.orElse(ANY_FIELD));
            fields.add(expectedProvidedBy.orElse(ANY_FIELD));
            fields.add(expectedConfidence.orElse(ANY_FIELD));
            return fields;
        }

        public ExpectedPromiseRow withExpectedEffectiveDate(final String expected) {
            this.expectedEffectiveDate = Optional.of(expected);
            return this;
        }

        public ExpectedPromiseRow withExpectedAsin(final String expected) {
            this.expectedAsin = Optional.of(expected);
            return this;
        }

        public ExpectedPromiseRow withExpectedItemId(final String expected) {
            this.expectedItemId = Optional.of(expected);
            return this;
        }

        public ExpectedPromiseRow withExpectedActive(final String expected) {
            this.expectedActive = Optional.of(expected);
            return this;
        }

        public ExpectedPromiseRow withExpectedPromisedShipDate(final String expected) {
            this.expectedPromisedShipDate = Optional.of(expected);
            return this;
        }

        public ExpectedPromiseRow withExpectedPromisedDeliveryDate(final String expected) {
            this.expectedPromisedDeliveryDate = Optional.of(expected);
            return this;
        }

        public ExpectedPromiseRow withExpectedDeliveryDate(final String expected) {
            this.expectedDeliveryDate = Optional.of(expected);
            return this;
        }

        public ExpectedPromiseRow withExpectedProvidedBy(final String expected) {
            this.expectedProvidedBy = Optional.of(expected);
            return this;
        }

        public ExpectedPromiseRow withExpectedConfidence(final String expected) {
            this.expectedConfidence = Optional.of(expected);
            return this;
        }
    }

    private static class ExpectedOrderRow {
        private Optional<String> expectedOrderDate = Optional.empty();
        private Optional<String> expectedOrderId = Optional.empty();
        private Optional<String> expectedMarketplace = Optional.empty();
        private Optional<String> expectedTimezone = Optional.empty();
        private Optional<String> expectedCondition = Optional.empty();
        private Optional<String> expectedShipOption = Optional.empty();
        private Optional<String> expectedCustomerId = Optional.empty();

        public List<String> getFields() {
            List<String> fields = new ArrayList<>(10);
            fields.add(expectedOrderDate.orElse(ANY_FIELD));
            fields.add(expectedOrderId.orElse(ANY_FIELD));
            fields.add(expectedMarketplace.orElse(ANY_DIGITS));
            fields.add(expectedTimezone.orElse(ANY_ACTIVE));
            fields.add(expectedCondition.orElse(ANY_FIELD));
            fields.add(expectedShipOption.orElse(ANY_FIELD));
            fields.add(expectedCustomerId.orElse(ANY_FIELD));

            return fields;

        }

        public ExpectedOrderRow withExpectedOrderDate(final String expected) {
            this.expectedOrderDate = Optional.of(expected);
            return this;
        }

        public ExpectedOrderRow withExpectedOrderId(final String expected) {
            this.expectedOrderId = Optional.of(expected);
            return this;
        }

        public ExpectedOrderRow withExpectedMarketplace(final String expected) {
            this.expectedMarketplace = Optional.of(expected);
            return this;
        }

        public ExpectedOrderRow withExpectedTimezone(final String expected) {
            this.expectedTimezone = Optional.of(expected);
            return this;
        }

        public ExpectedOrderRow withExpectedCondition(final String expected) {
            this.expectedCondition = Optional.of(expected);
            return this;
        }

        public ExpectedOrderRow withExpectedShipOption(final String expected) {
            this.expectedShipOption = Optional.of(expected);
            return this;
        }

        public ExpectedOrderRow withExpectedCustomerId(final String expected) {
            this.expectedCustomerId = Optional.of(expected);
            return this;
        }
    }
}
