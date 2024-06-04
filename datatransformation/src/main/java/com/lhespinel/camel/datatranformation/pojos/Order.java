package com.lhespinel.camel.datatranformation.pojos;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
    private int id;
    private String owner;
    private Items items;
}
