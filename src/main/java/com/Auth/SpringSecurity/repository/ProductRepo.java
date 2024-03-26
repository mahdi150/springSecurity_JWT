package com.Auth.SpringSecurity.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.Auth.SpringSecurity.entity.Product;

public interface ProductRepo extends JpaRepository<Product,Integer> {

}
