package com.lhespinel.camel.datatranformation.pojos;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory(){

    }

    public Orders createOrders(){
        return new Orders();
    }

}
