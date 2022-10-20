package com.jwctech.giftregistry.service;

import com.jwctech.giftregistry.payload.ProductPayload;

public interface ProductService {

    ProductPayload createProduct(Long id, String productUrl);

    ProductPayload updateProduct(Long id, ProductPayload productPayload);

}
