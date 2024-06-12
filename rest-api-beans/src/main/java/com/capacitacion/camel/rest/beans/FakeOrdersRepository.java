package com.capacitacion.camel.rest.beans;

import com.capacitacion.camel.rest.pojos.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FakeOrdersRepository {
    private final List<Order> orders;

    public FakeOrdersRepository(){
        this.orders = new ArrayList<>();
    }

    public List<Order> saveAll(List<Order> list){
        orders.addAll(list);
        log.info("New total Orders: {}", orders.size());
        return orders;
    }


}
