package com.lhespinel.camel.datatranformation.pojos.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Vista {

    private Integer id;

    private String acronimo;

    private String descripcion;

    private String aplicacion;

    private List<Roles> roles;

}