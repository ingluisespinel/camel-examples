package com.capacitacion.camel.rest.aggregators;

import com.capacitacion.camel.rest.pojos.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.List;

@Slf4j
public class OrdersAggregator implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        int currentPage = newExchange.getIn().getHeader("currentPage", Integer.class);
        List<Order> newOrders = newExchange.getIn().getBody(List.class);
        log.info("NewOrders list {}", newOrders);
        if(newOrders != null && newOrders.size() == 10){
            // Set the current page + 1 in order to get the next page in the next request
            currentPage ++;
        } else {
            // Set the currentPage to -1 in order to avoid make more http requests
            currentPage = -1;
        }
        oldExchange.getIn().setHeader("currentPage", currentPage);
        if(oldExchange.getIn().getBody() == null){
            oldExchange.getIn().setBody(newOrders);
            return oldExchange;
        }
        List<Order> oldOrders = oldExchange.getIn().getBody(List.class);
        // Aggregate old order + new orders
        newOrders.addAll(oldOrders);
        oldExchange.getIn().setBody(newOrders);
        return oldExchange;
    }
}
