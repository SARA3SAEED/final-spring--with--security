package com.PointOfSales.POS.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailsRequest {

    private Double total;
    private Integer qty;
    private Integer price;
    private Integer barcod;
    private String orderno;
}
