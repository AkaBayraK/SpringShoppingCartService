package com.demo.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data 
@Entity
public class ShoppingCartEntity {
	
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private Long id;

	  private String shoppincartName;

	  @ManyToMany
	  @JoinTable(
	      name = "shopping_cart_product",
	      joinColumns = @JoinColumn(name = "shopping_cart_id"),
	      inverseJoinColumns = @JoinColumn(name = "product_id"))
	  Set<ProductEntity> products;
	  

}
