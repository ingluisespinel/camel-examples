package com.camel.bases.routes;

import org.apache.camel.builder.RouteBuilder;

public class MoreRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:example?period=5000")
                .routeId("route-example-timer")
                .to("direct:loopDummy");

        from("direct:loopDummy")
                .loop(5)
                    .log("Current Index ${header.CamelLoopIndex}")
                    .setBody(simple("${header.CamelLoopIndex}"))
                    .multicast()
                        .to("direct:routeA")
                        .to("direct:routeB")
                    .end()
                    .log("Finished Multicast")
                .end()
                .log("Finished loop");

        from("direct:routeA")
                .routeId("route-a")
                .log("${body}++");

        from("direct:routeB")
                .routeId("route-b")
                .log("${body}--");

    }
}
