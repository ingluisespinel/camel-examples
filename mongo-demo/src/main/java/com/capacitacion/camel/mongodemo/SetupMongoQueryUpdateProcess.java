package com.capacitacion.camel.mongodemo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.List;

public class SetupMongoQueryUpdateProcess implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String valueHeader = exchange.getIn().getHeader("CamelMongoDbCriteria", String.class);
        BasicDBObject filterQuery =  BasicDBObject.parse(valueHeader);
        BasicDBObject updateQuery = BasicDBObject.parse(exchange.getMessage().getBody(String.class));
        List<DBObject> query = new ArrayList<>();
        query.add(filterQuery);
        query.add(updateQuery);
        exchange.getIn().setBody(query);
    }
}
