package com.amazon.ata.deliveringonourpromise.data;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Responsible for generating valid OrderItemData.Builder objects for use by the OrderDatastore and/or
 * OrderDataGenerator.
 */
final class OrderItemDataGenerator {
    // default values
    private static final int DEFAULT_QUANTITY = 1;
    private static final String DEFAULT_SUPPLY_CODE = "65";

    // singleton instance
    private static final OrderItemDataGenerator ORDER_ITEM_GENERATOR_INSTANCE = new OrderItemDataGenerator();
    public static final String CONFIDENCE_TRACKING_PATTERN = "B0[13579].*";

    // data that increment as we generate more data
    private long currentOrderItemId = 20655079937521L;
    private long currentMerchantId = 14263472715L;
    private ZonedDateTime currentItemSupplyCodeDate = OrderDataGenerator.INITIAL_ORDER_DATE;
    private ZonedDateTime currentItemApprovalDate = ZonedDateTime.of(
        2009, 8, 4, 11, 8, 5, 6, ZoneId.ofOffset("UTC", ZoneOffset.UTC)
    );

    // counters for rotating through lists of valid values
    private int currentAsinIndex = 0;

    // for looking up ASINs/product titles
    private Map<String, String> titlesByAsin = new HashMap<>();
    private List<String> asinTitles = new ArrayList<>();

    private OrderItemDataGenerator() {
        populateAsinTitleMapping();
    }

    static OrderItemDataGenerator getGenerator() {
        return ORDER_ITEM_GENERATOR_INSTANCE;
    }


    /* Returns an OrderItemData builder with reasonable default values */
    OrderItemData.Builder buildOrderItemData() {
        String asin = createItemAsin();
        String title = createItemTitleForAsin(asin);
        return OrderItemData.builder()
                   .withCustomerOrderItemId(createCustomerOrderItemId())
                   .withAsin(asin)
                   .withTitle(title)
                   .withQuantity(createItemQuantity())
                   .withMerchantId(createItemMerchantId())
                   .withApprovalDate(createItemApprovalDate())
                   .withSupplyCode(createItemSupplyCode())
                   .withSupplyCodeDate(createItemSupplyCodeDate())
                   .withIsConfidenceTracked(createIsConfidenceTracked(asin))
                   .withConfidence(createConfidence(asin));
    }

    /*
     * Creates a List of one-entry maps from ASIN -> product title (for selecting ASIN/titles),
     * and creates a single lookup Map from ASIN -> title for finding the title to match a given ASIN.
     */
    private void populateAsinTitleMapping() {
        titlesByAsin.put("B01MZEEFNX", "Amazon Smart Plug - Simple set up, works with Alexa");
        titlesByAsin.put("B07BHHC4S1", "Architects of the West Kingdom");
        titlesByAsin.put("1984822179", "Normal People: A Novel");
        titlesByAsin.put("B000LQ78YY", "Stonepoint Emergency LED Road Flare Kit – "
                                               + "Set of 3 Super Bright LED Roadside Beacons with Magnetic Base");
        titlesByAsin.put("B0145IWKBE", "AmazonBasics Ladder Toss Set with Soft Carrying Case");
        titlesByAsin.put("B07MVQL5RT", "Greenworks 21-Inch 40V Brushless Push Mower, "
                                               + "6AH Battery and Charger Included, M-210");
        titlesByAsin.put("B0019QEB86", "Miracle-Gro Indoor Plant Food, 48-Spikes");
        titlesByAsin.put("B07FDNSJ63", "Mamma Mia! 2-Movie Collection");
        titlesByAsin.put("B01BKTAY2I", "My Doggy Place - Ultra Absorbent Microfiber Dog Door Mat");
        titlesByAsin.put("B06XYZBCYP", "Levoit Kana Himalayan/Hymilain Sea, "
                                               + "Pink Crystal Salt Rock Lamp, Night Light");

        asinTitles.addAll(titlesByAsin.keySet());
    }

    // helpers to deterministically return varying ORDER-ITEM data

    private String createCustomerOrderItemId() {
        currentOrderItemId += 13;
        return String.valueOf(currentOrderItemId);
    }

    private String createItemAsin() {
        currentAsinIndex = (currentAsinIndex + 1) % asinTitles.size();
        return asinTitles.get(currentAsinIndex);
    }

    private int createItemQuantity() {
        return DEFAULT_QUANTITY;
    }

    private String createItemTitleForAsin(String asin) {
        return titlesByAsin.get(asin);
    }

    private String createItemMerchantId() {
        currentMerchantId += 7;
        return String.valueOf(currentMerchantId);
    }

    private ZonedDateTime createItemApprovalDate() {
        currentItemApprovalDate = currentItemApprovalDate.plusMonths(1);
        return currentItemApprovalDate;
    }

    private String createItemSupplyCode() {
        return DEFAULT_SUPPLY_CODE;
    }

    private ZonedDateTime createItemSupplyCodeDate() {
        currentItemSupplyCodeDate = incrementDate(currentItemSupplyCodeDate);
        return currentItemSupplyCodeDate;
    }

    private boolean createIsConfidenceTracked(String asin) {
        return asin.matches(CONFIDENCE_TRACKING_PATTERN);
    }

    private int createConfidence(String asin) {
        if (asin.matches(CONFIDENCE_TRACKING_PATTERN)) {
            return new Random((int) asin.charAt(2)).nextInt(201) - 100;
        } else {
            // Simulate C-like random memory initialization (did you know hashCode() could be negative?)
            return (asin.hashCode() % 101);
        }
    }

    private ZonedDateTime incrementDate(ZonedDateTime dateTime) {
        return dateTime.plusHours(OrderDataGenerator.ORDER_DATA_DATE_HOURS_INCREMENT);
    }
}
