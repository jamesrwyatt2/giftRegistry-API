package com.jwctech.giftregistry.service;

import com.jwctech.giftregistry.payload.ProductPayload;

public interface ProductService {

    ProductPayload createProduct(Long id, ProductPayload productPayload);

    ProductPayload updateProduct(Long id, ProductPayload productPayload);

}
