package com.lhespinel.camel.restapi.components;

import com.lhespinel.camel.restapi.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import java.util.List;

@Slf4j
public class OrdersAggregator implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        int currentPage = newExchange.getMessage().getHeader("currentPage", Integer.class);
        List<Order> newOrders = newExchange.getMessage().getBody(List.class);
        log.info("NewOrders list {}", newOrders);
        if(newOrders != null && newOrders.size() == 10){
            // Set the current page + 1 in order to get the next page in the next request
            currentPage ++;
        } else {
            // Set the currentPage to -1 in order to avoid make more http requests
            currentPage = -1;
        }
        if(oldExchange == null || oldExchange.getMessage().getBody() == null){
            newExchange.getMessage().setHeader("currentPage", currentPage);
            return newExchange;
        }
        List<Order> oldOrders = oldExchange.getMessage().getBody(List.class);
        // Aggregate old order + new orders
        newOrders.addAll(oldOrders);
        oldExchange.getMessage().setBody(newOrders);
        oldExchange.getMessage().setHeader("currentPage", currentPage);
        return oldExchange;
    }
}
