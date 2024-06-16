package com.capacitacion.camel.datasources.models;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;

}