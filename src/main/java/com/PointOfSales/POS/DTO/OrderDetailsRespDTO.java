package com.PointOfSales.POS.DTO;

import com.PointOfSales.POS.Entity.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDetailsRespDTO {
    private List<OrderDetails> orderDetails;
    private Double total;
    private Integer qty;
    private Integer price;
    private Integer barcod;
    private String orderno;
}
