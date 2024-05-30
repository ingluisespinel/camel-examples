package com.camel.bases.components;

import com.camel.bases.domain.OrderStatus;
import com.camel.bases.domain.PurchaseOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Header;

@Slf4j
public class OrderProcessor {

    public PurchaseOrder processOrder(@Header("MyHeader") String myHeader,@Body PurchaseOrder order){
        log.info("Processing Order -> Header Value {}, Order Id {}", myHeader, order.getId());
        order.setStatus(OrderStatus.PROCESSED);
        return order;
    }

}
