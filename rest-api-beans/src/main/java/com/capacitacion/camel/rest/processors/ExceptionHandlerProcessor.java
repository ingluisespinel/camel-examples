package com.capacitacion.camel.rest.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ValidationException;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        // Set the HTTP Code Response
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
        exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/json");
        // Get the Caught Exception
        ValidationException exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, ValidationException.class);
        exchange.setProperty(Exchange.EXCEPTION_CAUGHT, null);
        Map<String, String> bodyResponse = new HashMap<>();
        bodyResponse.put("code", "INPUT_DATA_ERROR");
        bodyResponse.put("message", exception.getMessage());
        // Set the body response
        exchange.getOut().setBody(bodyResponse);
    }
}
