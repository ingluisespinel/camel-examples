package com.lhespinel.camel.datatranformation.pojos;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@ToString
@Data
@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Orders {
    private List<Order> order;

}