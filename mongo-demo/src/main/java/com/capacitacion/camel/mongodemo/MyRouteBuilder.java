package com.capacitacion.camel.mongodemo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.bson.types.ObjectId;

public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("jetty")
                .port(8080)
                .bindingMode(RestBindingMode.json);

        rest("/api/users")
                .get("/find-by-name")
                    .to("direct:findUserByName")
                .get("/{userId}")
                    .to("direct:findUserById");

        /*
            <route id="findUserRoute">
                <from uri="direct:findUser"/>
                <to uri="velocity:findQuery.vm"/>
                <to uri="mongodb:mongoClient?database=test&amp;collection=users&amp;operation=findAll"/>
                <log message="Query result: ${body}"/>
            </route>

         */

        from("direct:findUserByName")
                .to("velocity:findQuery.vm")
                .log("Body Content ${body} of class ${body.class}")
                .to("mongodb:mongoClient?database=test&collection=users&operation=findAll")
                .log("Body class after mongo query: ${body.class}")
                .log("Query result: ${body}");

        from("direct:findUserById")
                .setBody(header("userId"))
                .convertBodyTo(ObjectId.class)
                .log("Body Content ${body} of class ${body.class}")
                .to("mongodb:mongoClient?database=test&collection=users&operation=findById")
                .log("Body class after mongo query: ${body.class}")
                .log("Query result: ${body}");

    }
}
