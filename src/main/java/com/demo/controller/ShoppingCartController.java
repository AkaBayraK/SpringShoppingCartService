package com.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.ProductEntity;
import com.demo.entity.ShoppingCartEntity;
import com.demo.service.ShoppingCartService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RestController
@RequestMapping("shopping-cart")
public class ShoppingCartController {

  @Autowired ShoppingCartService shoppingCartService;

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML })
  @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML })
  public String testService() {
	  //biraz önce belirttimiz gibi ilk build ettiğimizde
	  //bu şekilde edeceğiz sonrasında ise (return "Shopping Service 2";)
	  //olarak build edeceğiz.
	  return "Shopping Service 1";
  }
  
  @PostMapping
  public ResponseEntity<ShoppingCartEntity> create(@RequestParam("name") String name) {
    return shoppingCartService.create(name);
  }

  @PostMapping("{id}")
  public ResponseEntity<ShoppingCartEntity> addProducts(@PathVariable("id") Long shoppingCartId, @RequestBody List<ProductEntity> products) {
    return shoppingCartService.addProducts(shoppingCartId, products);
  }

  @GetMapping("{id}")
  public ResponseEntity<Map<String, String>> getShoppingCartPrice(@PathVariable("id") Long shoppingCartId) {
    return shoppingCartService.getShoppingCartPrice(shoppingCartId);
  }
}