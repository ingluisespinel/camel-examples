package com.lhespinel.camel.restapi.repository;

import com.lhespinel.camel.restapi.model.Order;
import lombok.Builder;
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
