package com.amazon.ata.deliveringonourpromise.data;

import com.amazon.ata.string.TextTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Creates sample Order data and associated Order-Item data that is internally consistent (e.g. order IDs
 * and order item IDs should all agree for the same order). Will return order data for any
 * valid order ID provided, but will only return order-item data for order-items associated with
 * the sample orders stored in the datastore.
 * <p>
 * Also maintains a set of specific order IDs that are reserved for specific cases, which can be retrieved via
 * {@code orderDatastore.getFixtures()}
 * </p>
 * <p>
 * OrderData objects contain order information, including a List of OrderItemData objects, corresponding
 * to each customer order item.
 * </p>
 */
public final class OrderDatastore {
    private static final String ORDER_FORMAT = "\\d{3}-\\d{7}-\\d{7}";

    private static final OrderDatastore ORDER_DATASTORE_INSTANCE = new OrderDatastore();

    // data generators, which produce builder objects of appropriate types
    private final OrderDataGenerator orderDataGenerator = OrderDataGenerator.getGenerator();
    private final OrderShipmentDataGenerator orderShipmentDataGenerator = OrderShipmentDataGenerator.getGenerator();
    private final OrderItemDataGenerator orderItemDataGenerator = OrderItemDataGenerator.getGenerator();
    private final OrderFixtureGenerator orderFixtureGenerator = OrderFixtureGenerator.getGenerator();

    // sample data state
    private boolean isDataPopulated = false;

    // storage of data created so far
    private List<OrderData> orderDatasLookup = new ArrayList<>();
    private Map<String, OrderItemData> orderItemDatasByOrderItemId = new HashMap<>();
    private Map<String, OrderFixture> orderFixtures = new HashMap<>();
    private Map<String, OrderItemData> orderFixtureItemData = new HashMap<>();

    private OrderDatastore() {
    }

    /**
     * Returns an OrderDatastore instance suitable for returning order data.
     *
     * @return an OrderDatastore instance
     */
    public static OrderDatastore getDatastore() {
        return ORDER_DATASTORE_INSTANCE;
    }

    /**
     * Get OrderData for the given order ID. Will return valid data if order ID is well-formed. Otherwise
     * may return data, or may throw IllegalArgumentException.
     *
     * @param orderId The order ID to fetch sample data for
     * @return OrderData object with zero to many OrderItemData entries. orderIds will all be set to orderId.
     */
    public OrderData getOrderData(String orderId) {
        ensureDataPopulated();

        // return null if malformed
        if (null == orderId || !Pattern.matches(ORDER_FORMAT, orderId)) {
            return null;
        }

        if (orderFixtures.containsKey(orderId)) {
            return orderFixtures.get(orderId).getOrderData();
        }

        OrderData orderData = orderDatasLookup.get(orderIdToIndex(orderId));
        orderData.setOrderId(orderId);
        for (OrderItemData itemData : orderData.getCustomerOrderItemList()) {
            itemData.setOrderId(orderId);
        }

        return orderData;
    }

    /**
     * Returns OrderItemData for the given customer order item ID. Will only return non-null if the
     * given customerOrderItemId corresponds to one of the sample data Orders.
     *
     * @param customerOrderItemId customer order item ID to fetch order-item data for
     * @return OrderItemData corresponding to the given ID if found. Otherwise, returns null
     */
    public OrderItemData getOrderItemData(String customerOrderItemId) {
        ensureDataPopulated();

        if (orderFixtureItemData.containsKey(customerOrderItemId)) {
            return orderFixtureItemData.get(customerOrderItemId);
        }

        return orderItemDatasByOrderItemId.get(customerOrderItemId);
    }

    /**
     * Returns the number of distinct order records in the datastore.
     *
     * @return the number of distinct order records returnable by the datastore
     */
    public int getNumOrders() {
        ensureDataPopulated();
        return orderDatasLookup.size();
    }

    /**
     * Returns all of the order fixtures. Changes to the Map will not affect OrderDatastore's data.
     *
     * @return Map from order ID : OrderFixture
     */
    public Map<String, OrderFixture> getOrderFixtures() {
        ensureDataPopulated();

        return new HashMap<>(orderFixtures);
    }

    /**
     * Returns table formatted String containing all order fixtures.
     *
     * @return Text table containing all of the order fixtures, including their description and order item IDs
     */
    public String getOrderFixturesTable() {
        ensureDataPopulated();

        Map<String, OrderFixture> orderFixtures = getOrderFixtures();
        List<String> headers = Arrays.asList("ORDER ID",
                                             "# ITEMS",
                                             "# SHIPMENTS",
                                             "# SHIPPED",
                                             "# DELIVERED",
                                             "DPS & OFS PROMISES AGREE?",
                                             "PROMISE CONFIDENCE?",
                                             "DESCRIPTION");
        List<List<String>> dataRows = new ArrayList<>();

        List<String> orderIds = new ArrayList<>(orderFixtures.keySet());
        orderIds.sort(String::compareTo);

        for (String orderId : orderIds) {
            OrderFixture fixture = orderFixtures.get(orderId);

            OrderData orderData = fixture.getOrderData();

            if (null == orderData) {
                dataRows.add(Arrays.asList(orderId,
                                           formatTableNumberEntry(0),
                                           formatTableNumberEntry(0),
                                           formatTableNumberEntry(0),
                                           formatTableNumberEntry(0),
                                           "",
                                           "",
                                           fixture.getDataDescription()
                             )
                );
            } else {
                dataRows.add(Arrays.asList(orderId,
                                           formatTableNumberEntry(getNumItemsForOrder(orderData)),
                                           formatTableNumberEntry(getNumShipmentsForOrder(orderData)),
                                           formatTableNumberEntry(getNumShippedShipmentsForOrder(orderData)),
                                           formatTableNumberEntry(getNumDeliveredShipmentsForOrder(orderData)),
                                           renderPromiseAgreementEntryForOrder(orderData),
                                           renderPromiseConfidenceEntryForOrder(orderData),
                                           fixture.getDataDescription()
                             )
                );
            }
        }

        return new TextTable(headers, dataRows).toString();
    }

    /*
     *  lazily populate orders, their shipments and order items.
     */
    private synchronized void ensureDataPopulated() {
        if (!isDataPopulated) {

            // samples with different numbers of order items
            orderDatasLookup.add(orderDataGenerator.buildOrderData(1).build());
            orderDatasLookup.add(orderDataGenerator.buildOrderData(2).build());
            orderDatasLookup.add(orderDataGenerator.buildOrderData(2).build());
            orderDatasLookup.add(orderDataGenerator.buildOrderData(2).build());
            orderDatasLookup.add(orderDataGenerator.buildOrderData(3).build());
            orderDatasLookup.add(orderDataGenerator.buildOrderData(3).build());
            orderDatasLookup.add(orderDataGenerator.buildOrderData(4).build());
            orderDatasLookup.add(orderDataGenerator.buildOrderData(10).build());

            // fetch and store fixtures
            orderFixtures.putAll(orderFixtureGenerator.generateOrderFixtures());

            // populate order items fixtures lookup
            for (Map.Entry<String, OrderFixture> orderFixtureEntry : orderFixtures.entrySet()) {
                // skip any fixtures representing missing orders
                if (orderFixtureEntry.getValue().getOrderData() == null) {
                    continue;
                }
                for (OrderItemData orderItem : orderFixtureEntry.getValue().getOrderData().getCustomerOrderItemList()) {
                    orderFixtureItemData.put(orderItem.getCustomerOrderItemId(), orderItem);
                }
            }

            // now populate the order items lookup
            for (OrderData orderData : orderDatasLookup) {
                for (OrderItemData orderItemData : orderData.getCustomerOrderItemList()) {
                    orderItemDatasByOrderItemId.put(orderItemData.getCustomerOrderItemId(), orderItemData);
                }
            }

            isDataPopulated = true;
        }
    }

    /*
     * Computes hash for the given orderId. Make it simple, based on the digits after the last '-' in the order
     * ID, so that one can 'walk' through the sample data by incrementing the order ID suffix.
     */
    private int orderIdToIndex(String orderId) {
        return getOrderIdSuffixInt(orderId) % orderDatasLookup.size();
    }

    /*
     * Given an order ID, extracts the int after the last hyphen. raises IllegalArgumentException if expected format
     * not found.
     */
    private int getOrderIdSuffixInt(String orderId) {
        String[] idSegments = orderId.split("-");

        if (idSegments.length != 3) {
            throw new IllegalArgumentException(String.format(
                "Expected three segments in order ID '%s', but found %d", orderId, idSegments.length
            ));
        }

        String suffix = idSegments[2];
        if (suffix.length() != 7) {
            throw new IllegalArgumentException(String.format(
                "Expected order ID ('%s') suffix to contain 7 digits, but suffix was '%s' instead", orderId, suffix
            ));
        }

        return Integer.parseInt(suffix);
    }

    /*
     * For an int entry in a data table, format the integer for table display (e.g. instead of "0", print "" for
     * easier readability).
     */
    private String formatTableNumberEntry(int entry) {
        if (0 == entry) {
            return "";
        } else {
            return String.valueOf(entry);
        }
    }

    /*
     * Returns number of items in the given order.
     */
    private int getNumItemsForOrder(OrderData orderData) {
        return orderData.getCustomerOrderItemList().size();
    }

    /*
     * Returns number of shipments in the given order.
     */
    private int getNumShipmentsForOrder(OrderData orderData) {
        return orderData.getCustomerShipments().size();
    }

    /*
     * Returns number of shipped shipments in the given order.
     */
    private int getNumShippedShipmentsForOrder(OrderData orderData) {
        return orderData.getCustomerShipments().stream()
                   .map(s -> {
                       if (s.getShipDate() == null) {
                           return 0;
                       } else {
                           return 1;
                       }
                   })
                   .reduce(0, Integer::sum);
    }

    /*
     * Returns number of delivered shipments in the given order.
     */
    private int getNumDeliveredShipmentsForOrder(OrderData orderData) {
        return orderData.getCustomerShipments().stream()
                   .map(s -> {
                       if (s.getDeliveryDate() == null) {
                           return 0;
                       } else {
                           return 1;
                       }
                   })
                   .reduce(0, Integer::sum);
    }

    /*
     * Returns display for whether promises agree for shipments in the given order.
     */
    private String renderPromiseAgreementEntryForOrder(OrderData orderData) {
        int numPromisesAgree = 0;
        int numPromisesDisagree = 0;

        for (OrderShipmentData shipment : orderData.getCustomerShipments()) {
            if (shipmentPromisesAgree(shipment)) {
                numPromisesAgree++;
            } else {
                numPromisesDisagree++;
            }
        }

        String display;

        if (numPromisesAgree > 0 && numPromisesDisagree > 0) {
            display = "MIXED";
        } else if (numPromisesAgree > 0) {
            display = "Y";
        } else if (numPromisesDisagree > 0) {
            display = "N";
        } else {
            // there are no shipments/promises, don't display anything
            display = "";
        }

        return display;
    }

    /*
     * Returns whether a given shipment's promises agree.
     */
    private boolean shipmentPromisesAgree(OrderShipmentData shipment) {
        return shipment.doDpsAndOfsPromisesAgree();
    }

    /*
     * Returns display for whether a given shipment's items have positive, negative, untracked or mixed promise
     * confidence.
     */
    private String renderPromiseConfidenceEntryForOrder(OrderData orderData) {
        int numPositive = 0;
        int numNegative = 0;
        int numUntracked = 0;

        for (OrderItemData orderItemData : orderData.getCustomerOrderItemList()) {
            switch (confidenceStatusForItem(orderItemData)) {
                case UNTRACKED:
                    numUntracked++;
                    break;
                case POSITIVE:
                    numPositive++;
                    break;
                case NEGATIVE:
                    numNegative++;
                    break;
                default:
                    // do nothing
                    break;
            }
        }

        String display;
        if ((numPositive > 0 && numNegative > 0)
            || (numPositive > 0 && numUntracked > 0)
            || (numNegative > 0 && numUntracked > 0)) {

            display = "MIXED";
        } else if (numPositive > 0) {
            display = "POSITIVE";
        } else if (numNegative > 0) {
            display = "NEGATIVE";
        } else if (numUntracked > 0) {
            display = "UNTRACKED";
        } else {
            display = "";
        }

        return display;
    }

    /*
     * returns whether a given order item has positive, negative, or untracked promise confidence.
     */
    private ConfidenceStatus confidenceStatusForItem(OrderItemData orderItem) {
        if (!orderItem.isConfidenceTracked()) {
            return ConfidenceStatus.UNTRACKED;
        } else if (orderItem.getConfidence() >= 0) {
            return ConfidenceStatus.POSITIVE;
        } else {
            return ConfidenceStatus.NEGATIVE;
        }
    }

    private enum ConfidenceStatus {
        POSITIVE, NEGATIVE, UNTRACKED;
    }

}
