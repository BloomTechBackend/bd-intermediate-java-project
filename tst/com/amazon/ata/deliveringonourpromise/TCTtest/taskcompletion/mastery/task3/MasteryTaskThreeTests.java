package com.amazon.ata.deliveringonourpromise.TCTtest.taskcompletion.mastery.task3;

import com.amazon.ata.deliveringonourpromise.types.Order;
import com.amazon.ata.deliveringonourpromise.types.OrderItem;

import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class MasteryTaskThreeTests {

    @Test
    public void masteryTaskThree_orderClass_internalStateIsNotExposed() {
        // GIVEN
        Class<?> orderClass = Order.class;

        // WHEN
        Field[] declaredFields = orderClass.getDeclaredFields();

        // THEN
        assertTrue(0 < declaredFields.length, "Expected Order class to contain fields!");

        for (Field declaredField : declaredFields) {
            boolean isFieldPrivate = Modifier.isPrivate(declaredField.getModifiers());
            assertTrue(isFieldPrivate, " Expected Order class fields to not be directly accessible!");
        }
    }

    @Test
    void masteryTaskThree_orderClass_internalStateIsUnmodifiable() {
        // GIVEN
        Class<?> orderClass = Order.class;

        // WHEN
        Method[] declaredMethods = orderClass.getDeclaredMethods();

        // THEN
        assertTrue(0 < declaredMethods.length, "Expected Order class methods to exist!");

        for (Method declaredMethod : declaredMethods) {
            String methodName = declaredMethod.getName();
            assertFalse(methodName.startsWith("set"), "Expected Order class fields to not be modified!");
        }
    }

    @Test
    void masteryTaskThree_orderClass_getCustomerOrderItemList_internalStateIsProtectedByDefensiveCopying() {
        // GIVEN
        OrderItem customerOrderItem = OrderItem.builder()
            .withCustomerOrderItemId("1")
            .build();

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(customerOrderItem);

        Order order = Order.builder()
            .withCustomerOrderItemList(orderItemList)
            .build();

        String maliciousCustomerOrderItemId = "2";
        OrderItem maliciousCustomerOrderItem = OrderItem.builder()
            .withCustomerOrderItemId(maliciousCustomerOrderItemId)
            .build();

        // WHEN
        List<OrderItem> customerOrderItemList = order.getCustomerOrderItemList();
        // trying to remove elements should fail and/or not change Order's list
        // CHECKSTYLE:OFF:EmptyBlock
        try {
            customerOrderItemList.remove(0);
        } catch (UnsupportedOperationException e) {
            // exception acceptable if list unmodified
        }

        // trying to add elements should fail and/or not change Order's list
        try {
            customerOrderItemList.add(maliciousCustomerOrderItem);
        } catch (UnsupportedOperationException e) {
            // exception acceptable if list unmodified
        }
        // CHECKSTYLE:ON:EmptyBlock

        // THEN
        assertEquals(
            order.getCustomerOrderItemList().size(),
            1,
            "Expected only original OrderItem to exist in Order, but found: " + order.getCustomerOrderItemList()
        );
        String orderItemId = order.getCustomerOrderItemList().get(0).getCustomerOrderItemId();
        assertNotEquals(orderItemId, maliciousCustomerOrderItemId,
            "Expected Order class to not allow item to be maliciously inserted but it was!");
    }

    @Test
    void masteryTaskThree_orderClass_withCustomerOrderItemList_internalStateIsProtectedByDefensiveCopying() {
        // GIVEN
        OrderItem customerOrderItem = OrderItem.builder()
                                          .withCustomerOrderItemId("1")
                                          .build();

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(customerOrderItem);

        // Don't finish building the Order just yet...pass in the list,
        // then modify it before final build().
        Order.Builder orderBuilder = Order.builder()
                          .withCustomerOrderItemList(orderItemList);

        String maliciousCustomerOrderItemId = "2";
        OrderItem maliciousCustomerOrderItem = OrderItem.builder()
                                                   .withCustomerOrderItemId(maliciousCustomerOrderItemId)
                                                   .build();

        // WHEN - attempt to update the list that was already passed into the Order should
        // not modify the Order's list, even if modified before build()
        orderItemList.add(maliciousCustomerOrderItem);

        // THEN
        Order order = orderBuilder.build();
        List<OrderItem> customerOrderItemList = order.getCustomerOrderItemList();
        assertEquals(
            customerOrderItemList.size(),
            1,
            "Expected only original OrderItem to exist in Order, but found: " + customerOrderItemList
        );
        String orderItemId = customerOrderItemList.get(0).getCustomerOrderItemId();
        assertNotEquals(orderItemId, maliciousCustomerOrderItemId,
                        "Expected Order class to not allow item to be maliciously inserted but it was!");
    }
}
