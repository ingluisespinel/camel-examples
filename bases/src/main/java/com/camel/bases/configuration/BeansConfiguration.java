package com.camel.bases.configuration;

import com.camel.bases.components.DataGenerator;
import com.camel.bases.components.OrderProcessor;
import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;

/**
 * Class to configure the Camel application.
 */
@Configuration
public class BeansConfiguration {

    @BindToRegistry("orderGenerator")
    public DataGenerator dataGenerator() {
        return new DataGenerator();
    }

    @BindToRegistry("orderProcessor")
    public OrderProcessor createOrderProcessor(){
        return new OrderProcessor();
    }

}
