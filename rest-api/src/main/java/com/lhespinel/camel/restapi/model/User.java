package com.lhespinel.camel.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String rol;

    @Override
    public boolean equals(Object u){
        if(u instanceof User){
            return this.id.equals(((User) u).getId());
        }
        return false;
    }
}
