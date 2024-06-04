package com.lhespinel.camel.datatranformation.pojos;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@ToString
@Data
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
    private int id;
    private String name;
    private int amount;

}

