package com.byteMe;
import com.byteMe.models.MenuItem;
import com.byteMe.models.Order;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class OrderTest {

    @org.junit.Test
    @Test
    public void testOrderOutOfStockItem() {
        MenuItem coke = new MenuItem("Coke",40.0, "Drink", false);
        List<MenuItem> items = List.of(coke);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Order("test@example.com",items, false);
        });

        assertEquals("Item Coke is out of stock and cannot be ordered.", exception.getMessage());
    }
}
