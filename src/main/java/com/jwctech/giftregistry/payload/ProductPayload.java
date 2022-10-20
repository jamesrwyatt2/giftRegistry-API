package com.jwctech.giftregistry.payload;

import lombok.Data;

@Data
public class ProductPayload {

    private Long id;
    private String url;
    private String title;
    private String price;
    private String description;
    private String image;
    private String icon;

}
