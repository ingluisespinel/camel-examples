package com.camel.bases.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PurchaseOrder {
    private int id;
    private OrderStatus status;
    private List<Item> items;

    public double getTotal(){
        return items.stream().mapToInt(Item::getAmount).sum();
    }
}
