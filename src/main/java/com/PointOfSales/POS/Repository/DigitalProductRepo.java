package com.PointOfSales.POS.Repository;

import com.PointOfSales.POS.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface DigitalProductRepo extends JpaRepository<Product, Integer> {
}
