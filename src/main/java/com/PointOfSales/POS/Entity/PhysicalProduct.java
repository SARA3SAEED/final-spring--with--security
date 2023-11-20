package com.PointOfSales.POS.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "PHYSICAL_PRD")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhysicalProduct extends Product{
    private Double weight;
    private Double height;
}
