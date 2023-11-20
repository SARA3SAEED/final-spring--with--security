package com.PointOfSales.POS.Repository;

import com.PointOfSales.POS.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {
    Optional<Order> findByOrderno(String orderNo);

    boolean deleteOrderById(Integer Id);

}
