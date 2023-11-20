package com.PointOfSales.POS.DTO;

import com.PointOfSales.POS.Entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
@AllArgsConstructor
@Data
@Builder
public class UpdateOrderReqDTO {
    private OrderStatus orderStatus;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
