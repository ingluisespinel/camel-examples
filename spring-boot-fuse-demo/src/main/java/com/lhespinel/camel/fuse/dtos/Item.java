package com.lhespinel.camel.fuse.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Item {
    private String name;
    private int amount;
}