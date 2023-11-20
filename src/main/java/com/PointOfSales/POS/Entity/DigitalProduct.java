package com.PointOfSales.POS.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "DIGITAL_PRD")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DigitalProduct extends Product{
    @Column(name = "serial_no")
    private String serialNo;
    private String provider;
}
