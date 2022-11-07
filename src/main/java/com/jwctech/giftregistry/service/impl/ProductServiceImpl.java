package com.jwctech.giftregistry.service.impl;

import com.jwctech.giftregistry.exception.ResourceNotFoundException;
import com.jwctech.giftregistry.model.Product;
import com.jwctech.giftregistry.model.iframely.IFramelyResponse;
import com.jwctech.giftregistry.payload.ProductPayload;
import com.jwctech.giftregistry.model.Registry;
import com.jwctech.giftregistry.repository.ProductRepo;
import com.jwctech.giftregistry.service.ProductService;
import com.jwctech.giftregistry.service.RegistryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Destination;
import java.sql.SQLException;

@Service
public class ProductServiceImpl implements ProductService {

    String APIKEY = "39314e19526e824fce0948";

    public final RestTemplate restTemplate;
    private ProductRepo productRepo;
    private RegistryService registryService;
    private ModelMapper mapper;

    public ProductServiceImpl(ProductRepo productRepo,
                              RegistryService registryService,
                              ModelMapper mapper,
                              RestTemplateBuilder restTemplateBuilder){
        this.productRepo=productRepo;
        this.registryService=registryService;
        this.mapper=mapper;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public ProductPayload createProduct(Long id, String productUrl) {
        Registry registry = registryService.findById(id);

        Product product = getProductInfo(productUrl);
        product.setRegistry(registry);

        return mapToPayload(productRepo.save(product));
    }

    @Override
    public ProductPayload updateProduct(Long id, ProductPayload productPayload) {
        Product product = productRepo.findById(productPayload.getId()).orElseThrow(() -> new ResourceNotFoundException("Product","id", id.toString()));
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product","id", id.toString()));
        productRepo.delete(product);
    }

    public Product getProductInfo(String productUrl){

        String url ="https://iframe.ly/api/iframely?url="+productUrl+"&api_key="+APIKEY;
        Product tempProduct;
        try{
            IFramelyResponse productResponse = this.restTemplate.getForObject(url, IFramelyResponse.class);
            System.out.println(productResponse.toString());
            if(productResponse.getError()!=null){
                System.out.println("Exception Issue with call "+productResponse.getError());
                throw new ResourceNotFoundException("Product", "URL", productUrl + " | "+ productResponse.getError());
            }
            tempProduct = mapResponseToProduct(productResponse);

        } catch (HttpStatusCodeException ex) {
            System.out.println("iFramily Service failed for: "+ ex);
            // raw http status code e.g `404`
            System.out.println(ex.getRawStatusCode());
            // http status code e.g. `404 NOT_FOUND`
           // System.out.println(ex.getStatusCode().toString());
            // get response body
            //System.out.println(ex.getResponseBodyAsString());
            // get http headers
            //HttpHeaders headers= ex.getResponseHeaders();
            // System.out.println(headers.get("Content-Type"));
            // System.out.println(headers.get("Server"));

            throw new ResourceNotFoundException("Product", "URL", productUrl);


        }
        return tempProduct;
    }

    public Product mapResponseToProduct(IFramelyResponse productResponse){

        Product product = new Product();
        product.setUrl(productResponse.getUrl());
        product.setTitle(productResponse.getMeta().getTitle());
        try {
            product.setDescription(productResponse.getMeta().getDescription());
        } catch (Exception ex){

        }
        product.setPrice(String.valueOf(productResponse.getMeta().getPrice()));
        product.setImage(productResponse.getLinks().getThumbnail().get(0).getHref());
        product.setIcon(productResponse.getLinks().getIcon().get(0).getHref());

        product.setHtml(productResponse.getHtml());

        product.setMedium(productResponse.getMeta().getMedium());
        product.setBrand(productResponse.getMeta().getBrand());
        product.setCategory(productResponse.getMeta().getCategory());

        return product;
    }

    public Product mapToProduct(IFramelyResponse productResponse, Registry registry){
        Product product = mapper.map(productResponse, Product.class);

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
