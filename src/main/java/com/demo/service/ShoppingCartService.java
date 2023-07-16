package com.demo.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.entity.ProductEntity;
import com.demo.entity.ShoppingCartEntity;
import com.demo.repository.ProductRepository;
import com.demo.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {

  @Autowired ShoppingCartRepository shoppingCartRepository;
  @Autowired ProductRepository productRepository;
  @Autowired RestTemplate restTemplate;

  public ResponseEntity<ShoppingCartEntity> create(String name) {
	  ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
    shoppingCart.setShoppincartName(name);
    return ResponseEntity.ok().body(shoppingCartRepository.save(shoppingCart));
  }

  public ResponseEntity<ShoppingCartEntity> addProducts(Long shoppingCartId, List<ProductEntity> products) {
	  ShoppingCartEntity shoppingCart =
        shoppingCartRepository
            .findById(shoppingCartId)
            .orElseThrow(() -> new RuntimeException("Verilen id ile eşleşen bir sonuç bulunamadı"));

    products.forEach(product -> productRepository.saveAndFlush(product));

    Set<ProductEntity> newProducts = new HashSet(products);
    shoppingCart.setProducts(newProducts);
    return ResponseEntity.ok().body(shoppingCartRepository.save(shoppingCart));
  }

  public ResponseEntity<Map<String, String>> getShoppingCartPrice(Long shoppingCartId) {
    Map<String, String> response = new HashMap();

    ShoppingCartEntity shoppingCart =
        shoppingCartRepository
            .findById(shoppingCartId)
            .orElseThrow(() -> new RuntimeException("Verilen id ile eşleşen bir sonuç bulunamadı"));

    int totalPrice =
        shoppingCart.getProducts().stream()
            .map(
                product ->
                    restTemplate.getForObject(
                        "http://product-service/product/" + product.getId(), HashMap.class))
            .mapToInt(productResponse -> (int) productResponse.get("price"))
            .sum();

    response.put("Total Price", Integer.toString(totalPrice));
    return ResponseEntity.ok().body(response);
  }
}
