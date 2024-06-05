package com.lhespinel.camel.fuse.components;

import com.lhespinel.camel.fuse.dtos.Item;
import com.lhespinel.camel.fuse.dtos.PurchaseOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PoDataGenerator {
    private final Random random;

    public PoDataGenerator(){
        random = new Random();
    }

    public List<PurchaseOrder> generateOrders(){
        ArrayList<PurchaseOrder> orders = new ArrayList<PurchaseOrder>();
        for (int i = 1; i <= 10; i++) {
            ArrayList<Item> items = new ArrayList<Item>();
            items.add(generateItem(i));
            PurchaseOrder order = PurchaseOrder.builder()
                    .id(i + 10)
                    .items(items)
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
