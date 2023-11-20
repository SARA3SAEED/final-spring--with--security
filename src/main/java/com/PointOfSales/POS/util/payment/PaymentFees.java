package com.PointOfSales.POS.util.payment;

public interface PaymentFees {
    double calTotalWithFees(Double total, PaymentMethods paymentMethod);
}
