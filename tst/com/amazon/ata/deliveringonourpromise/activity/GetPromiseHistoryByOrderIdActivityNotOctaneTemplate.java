package com.amazon.ata.deliveringonourpromise.activity;

import com.amazon.ata.deliveringonourpromise.dao.OrderDao;
import com.amazon.ata.deliveringonourpromise.dao.PromiseDao;
import com.amazon.ata.deliveringonourpromise.dao.ReadOnlyDao;
import com.amazon.ata.deliveringonourpromise.types.Order;
import com.amazon.ata.deliveringonourpromise.types.Promise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Ensures behavior of the Activity class in the base project that won't be true in the end state.
 */
public class GetPromiseHistoryByOrderIdActivityNotOctaneTemplate {
    private GetPromiseHistoryByOrderIdActivity activity;
    private ReadOnlyDao<String, Order> mockOrderDao;
    private ReadOnlyDao<String, List<Promise>> mockPromiseDao;

    @BeforeEach
    private void createLogic() {
        mockOrderDao = mock(OrderDao.class);
        mockPromiseDao = mock(PromiseDao.class);
        activity = new GetPromiseHistoryByOrderIdActivity(mockOrderDao, mockPromiseDao);
    }

    /**
     * This is part of Task 2: to find this NullPointerException in the Activity class.
     */
    @Test
    public void getPromiseHistoryByOrderId_nonNullInvalidOrderId_throwsException() {
        // GIVEN
        String invalidOrderId = "123";
        when(mockOrderDao.get(invalidOrderId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(NullPointerException.class, () -> activity.getPromiseHistoryByOrderId(invalidOrderId));
    }
}
