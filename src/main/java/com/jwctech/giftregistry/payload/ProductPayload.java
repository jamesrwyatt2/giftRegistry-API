package com.jwctech.giftregistry.payload;

import lombok.Data;

@Data
public class ProductPayload {

    private Long id;
    private String name;
    private String price;
    private String description;
}
