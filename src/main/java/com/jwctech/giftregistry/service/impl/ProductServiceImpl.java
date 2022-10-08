package com.jwctech.giftregistry.service.impl;

import com.jwctech.giftregistry.exception.ResourceNotFoundException;
import com.jwctech.giftregistry.model.Product;
import com.jwctech.giftregistry.payload.ProductPayload;
import com.jwctech.giftregistry.model.Registry;
import com.jwctech.giftregistry.repository.ProductRepo;
import com.jwctech.giftregistry.service.ProductService;
import com.jwctech.giftregistry.service.RegistryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepo productRepo;
    private RegistryService registryService;
    private ModelMapper mapper;

    public ProductServiceImpl(ProductRepo productRepo, RegistryService registryService, ModelMapper mapper){
        this.productRepo=productRepo;
        this.registryService=registryService;
        this.mapper=mapper;
    }

    @Override
    public ProductPayload createProduct(Long id, ProductPayload productPayload) {
        Registry registry = registryService.findById(id);

        Product product=mapToProduct(productPayload, registry);
        product.setRegistry(registry);

        return mapToPayload(productRepo.save(product));
    }

    @Override
    public ProductPayload updateProduct(Long id, ProductPayload productPayload) {
        Product product = productRepo.findById(productPayload.getId()).orElseThrow(() -> new ResourceNotFoundException("Product","id", id.toString()));
        return null;
    }

    public Product mapToProduct(ProductPayload productPayload, Registry registry){
        Product product = mapper.map(productPayload, Product.class);

//        Product product = new Product();
//        product.setId(productPayload.getId());
//        product.setName(productPayload.getName());
//        product.setDescription(productPayload.getDescription());
//        product.setPrice(productPayload.getPrice());
//        product.setRegistry(registry);

        return product;
    }
    public ProductPayload mapToPayload(Product product){
        ProductPayload payload = mapper.map(product, ProductPayload.class);

//        ProductPayload payload = new ProductPayload();
//        payload.setId(product.getId());
//        payload.setName(product.getName());
//        payload.setDescription(product.getDescription());
//        payload.setPrice(product.getPrice());
//        payload.setRegistry(registryService.mapToPayload(product.getRegistry()));

        return payload;
    }
}
