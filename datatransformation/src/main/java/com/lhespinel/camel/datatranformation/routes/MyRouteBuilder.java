package com.lhespinel.camel.datatranformation.routes;

import com.lhespinel.camel.datatranformation.pojos.Orders;
import com.lhespinel.camel.datatranformation.pojos.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;

import java.util.List;
import java.util.Map;


@Slf4j
public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:files/input?move=.done")
                .routeId("route-files-processor")
                .choice()
                    .when(header("CamelFileName").regex("^.*\\.xml"))
                        .to("direct:processXMLFile")
                    .when(header("CamelFileName").regex("^.*\\.json"))
                        .to("direct:processJSONFile")
                    .when(header("CamelFileName").regex("^.*\\.csv"))
                        .to("direct:processCSVFile")
                    .otherwise()
                        .log("Unexpected file extension");

        from("direct:processXMLFile")
                .routeId("route-process-xml")
                .unmarshal().jaxb(Orders.class.getPackageName()) // Convert XML Content to Java Objects
                .split().simple("${body.order}")
                    .to("direct:processOrder");

        from("direct:processOrder")
                .log("Processing Order ID ${body.id}")
                .marshal().jaxb() // Convert Java Object to XML Content
                .log("XML Body: ${body}");

        from("direct:processJSONFile")
                .routeId("route-process-json")
                .unmarshal().json(User[].class)
                .log("Users length: ${body.length}")
                // Access the data in a specific index using simple language
                .log("User data in index 0 -> UserName: ${body[0].name}, Id: ${body[0].id}")
                .marshal().json()
                .log("JSON String -> ${body}");

        from("direct:processCSVFile")
                .log("Processing csv data")
                //.unmarshal().csv() // Standard parser, return List<List<String>> and ',' delimiter
                .unmarshal(new CsvDataFormat().setUseMaps(true).setDelimiter(','))
                .process(exchange -> {
                    var body = (List<Map<String, Object>>) exchange.getMessage().getBody();
                    body.forEach(System.out::println);
                });

    }
}
