package com.PointOfSales.POS.Repository;

import com.PointOfSales.POS.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    Optional<Product> findByBarcod(Integer barcode);
}
