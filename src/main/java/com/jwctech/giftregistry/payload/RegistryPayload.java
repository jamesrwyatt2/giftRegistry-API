package com.jwctech.giftregistry.payload;

import com.jwctech.giftregistry.model.Product;
import lombok.Data;

import java.util.Set;

@Data
public class RegistryPayload {

    private Long id;
    private String title;
    private String description;
    private String date;
    private Set<ProductPayload> products;

}
