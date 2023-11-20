package com.PointOfSales.POS.Repository;

import com.PointOfSales.POS.Entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails,Integer> {
    List<OrderDetails> findAllByOrderNo(String orderNo);
}
