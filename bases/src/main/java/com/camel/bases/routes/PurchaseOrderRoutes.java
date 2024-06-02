package com.camel.bases.routes;

import com.camel.bases.domain.PurchaseOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;

@Slf4j
public class PurchaseOrderRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

//        from("timer:mainTimer?period={{mainTimerPeriod}}")
//                .routeId("route-main-timer")
//                .bean("orderGenerator", "generateOrders")
//                .log("Body size: ${body.size}")
//                .to("direct:processOrders");

        from("direct:processOrders")
                .routeId("route-orders-splitter")
                .split().body()
                    .process(exchange -> {
                        var order = exchange.getMessage().getBody(PurchaseOrder.class);
                        log.info("Split orderId {} with total {}", order.getId(), order.getTotal());
                    })
                    .to("direct:orderValidator")
                .end()// IMPORTANT: This end finish the split flow
                .log("Split Finished");

        from("direct:orderValidator")
                .routeId("route-order-validator")
                .log("Validating orderId ${body.id} ")
                .choice()
                    .when(simple("${body.total} > 500.0"))
                        .log("OrderId ${body.id} greater than 500")
                        .to("direct:orderFilter")
                    .otherwise()
                        .log("OrderId ${body.id} less than 500")
                .end()// IMPORTANT: This end finish the choice flow
                .log("Choice finish")
                .to("direct:orderFilter");

        from("direct:orderFilter")
                .routeId("route-order-filter")
                .log("Filtering orders with total greater than 500")
                .filter(simple("${body.total} > 800"))
                    .log("Filtered OrderId ${body.id} with total ${body.total}")
                    .to("direct:confirmOrder")
                    .stop() // IMPORTANT: Stop the filtered exchange
                .end() // IMPORTANT:  this 'end' finish the filter flow
                .log("After filter ${body.id} with total ${body.total}");

        from("direct:confirmOrder")
                .routeId("route-confirm-order")
                .setHeader("myHeader", constant("myHeaderValue"))
                .bean("orderProcessor", "processOrder")
                .log("---> Order ID ${body.id} final status: ${body.status}");


    }
}
