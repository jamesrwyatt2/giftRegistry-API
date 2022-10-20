package com.jwctech.giftregistry.controller;


import com.jwctech.giftregistry.payload.ProductPayload;
import com.jwctech.giftregistry.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/test")
    public ResponseEntity testService(){return ResponseEntity.ok("All good!");}

    @PostMapping
    @RequestMapping("/registry/{id}/product")
    public ResponseEntity createProduct(@PathVariable Long id, @RequestBody HashMap<String,String> url){
        System.out.println(url.get("url"));
        return ResponseEntity.ok(productService.createProduct(id, url.get("url")));
    }



    // Get all Products for a Registry - Not in uses
    @GetMapping("/registry/{id}/product")
    public ResponseEntity getProductsForRegistry(){
        return null;
    }
    //Update individual Product
    @GetMapping("/registry/{registryId}/product/{productId}")
    public ResponseEntity getProductById(@PathVariable Long id, @RequestBody ProductPayload uiProduct){


        return null;
    }
    //Update Product
    @PutMapping("/registry/{registryId}/product/{productId}")
    public ResponseEntity updateProduct(){
        return null;
    }
    //Delete Product
    @DeleteMapping("/registry/{registryId}/product/{productId}")
    public ResponseEntity deleteProduct(){
        return null;
    }
}
