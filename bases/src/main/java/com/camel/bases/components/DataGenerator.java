package com.camel.bases.components;

import com.camel.bases.domain.Item;
import com.camel.bases.domain.OrderStatus;
import com.camel.bases.domain.PurchaseOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class implemented for only test purposes
 */
public class DataGenerator {
    private final Random random;

    public DataGenerator(){
        random = new Random();
    }

    public List<PurchaseOrder> generateOrders(){
        var orders = new ArrayList<PurchaseOrder>();
        for (int i = 1; i <= 10; i++) {
            var order = PurchaseOrder.builder()
                    .id(i + 10)
                    .status(OrderStatus.CREATED)
                    .items(List.of(generateItem(i)))
                    .build();
            orders.add(order);
        }
        return orders;
    }

    private Item generateItem(int index){
        return Item.builder()
                .name("Item " + index)
                .amount(random.nextInt(1000) + 1)
                .build();
    }

}
