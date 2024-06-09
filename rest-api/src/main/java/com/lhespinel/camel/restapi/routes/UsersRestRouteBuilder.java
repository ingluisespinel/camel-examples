package com.lhespinel.camel.restapi.routes;

import com.lhespinel.camel.restapi.components.OrdersAggregator;
import com.lhespinel.camel.restapi.model.Order;
import com.lhespinel.camel.restapi.model.User;
import com.lhespinel.camel.restapi.processors.ExceptionHandlerProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.PropertyInject;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;

import static org.apache.camel.builder.AggregationStrategies.bean;

public class UsersRestRouteBuilder extends RouteBuilder {
    @PropertyInject("application.rest.pathBase")
    private String pathBase;
    @PropertyInject("application.rest.server.port")
    private int serverPort;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .contextPath(pathBase)
                .component("jetty") // App server to use for REST APIs exposition
                .host("0.0.0.0")
                .port(serverPort)
                .bindingMode(RestBindingMode.json)
                .apiContextPath("/api-docs")
                    .apiProperty("api.title", "Camel REST API")
                    .apiProperty("api.version", "1.0")
                    .apiProperty("cors", "true")
                    .apiProperty("api.specification.contentType.json", "application/vnd.oai.openapi+json;version=2.0")
                    .apiProperty("api.specification.contentType.yaml", "application/vnd.oai.openapi;version=2.0");

        rest("/users")
                .description("CRUD Users Services")
                .consumes("application/json")
                .produces("application/json")
                .get()
                    .to("direct:getUsers")
                .get("/{userId}")
                    .to("direct:getUserById")
                .post()
                    .type(User.class)
                    .to("direct:createUser")
                .delete("/{userId}")
                    .to("direct:deleteUser");

        rest("orders")
                .description("Service that process orders")
                .consumes("application/json")
                .produces("application/json")
                .post("/process")
                    .to("direct:processOrders");

        from("direct:getUsers")
                .log("Processing request GetUsers")
                .bean("fakeUsersRepository", "find");

        from("direct:getUserById")
                .log("Processing request GetUserById")
                .bean("fakeUsersRepository", "findById")
                .choice()
                .when(simple("${body} != null"))
                    .log("User found -> ${body}")
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .otherwise()
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
                .end();

        from("direct:deleteUser")
                .log("Processing request Delete User by User Id ${header.userId}")
                .bean("fakeUsersRepository", "delete")
                .choice()
                    .when(body().isEqualTo(constant(true)))
                        .log("User deleted.")
                        .setBody(constant(""))
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
                    .otherwise()
                        .log("User not found.")
                        .setBody(constant(""))
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
                .end();

        from("direct:createUser")
                .log("Precessing User creation request")
                .doTry()
                    .to("bean-validator:validateUser") // Bean validation execution
                    .bean("fakeUsersRepository", "save")
                    .log("New User ${body.id} created")
                .doCatch(ValidationException.class)
                    .log("Input data error")
                    .process(new ExceptionHandlerProcessor())
                    .marshal().json()
                .end()
                .log("Route finished");

        from("direct:processOrders")
                .log("Processing Orders")
                .setHeader("currentPage", constant(0))
                .loopDoWhile(header("currentPage").isGreaterThan(-1))
                    .enrich("direct:getExternalOrders", new OrdersAggregator())
                .end()
                .bean("fakeOrdersRepository", "saveAll");

        from("direct:getExternalOrders")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .log("Getting orders in page ${header.currentPage}")
                .toD("http://127.0.0.1:3000/api/v1/orders?page=${header.currentPage}&bridgeEndpoint=true")
                //.unmarshal().json(Order[].class)
                .unmarshal(new ListJacksonDataFormat(Order.class));

    }
}
