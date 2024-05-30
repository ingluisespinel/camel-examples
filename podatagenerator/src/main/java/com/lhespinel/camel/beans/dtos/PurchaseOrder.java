package com.lhespinel.camel.beans.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PurchaseOrder {
    private int id;
    private List<Item> items;

    public double getTotal(){
        return items.stream().mapToInt(Item::getAmount).sum();
    }
}
