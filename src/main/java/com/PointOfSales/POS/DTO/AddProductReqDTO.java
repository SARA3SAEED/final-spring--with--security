package com.PointOfSales.POS.DTO;

import com.PointOfSales.POS.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
@AllArgsConstructor
@Data
@Builder
public class AddProductReqDTO {
    private ProductType type;
    private Integer barcod;
    private String product_name;
    private String description;
    private Double price;

    // Digital
    private String serialNo;
    private String provider;

    // Physical
    private Double weight;
    private Double height;
}
