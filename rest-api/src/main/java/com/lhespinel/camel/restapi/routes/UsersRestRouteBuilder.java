package com.lhespinel.camel.restapi.routes;

import com.lhespinel.camel.restapi.model.User;
import com.lhespinel.camel.restapi.processors.ExceptionHandlerProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.PropertyInject;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

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
                .post()
                    .type(User.class)
                    .to("direct:createUser")
                .delete("/{userId}")
                    .to("direct:deleteUser");

        from("direct:getUsers")
                .log("Processing request GetUsers")
                .bean("fakeUsersRepository", "find");

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

    }
}
