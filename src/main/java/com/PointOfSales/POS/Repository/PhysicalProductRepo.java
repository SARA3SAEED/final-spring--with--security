package com.PointOfSales.POS.Repository;

import com.PointOfSales.POS.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PhysicalProductRepo extends JpaRepository<Product, Integer> {
}
