package com.PointOfSales.POS.util.payment;

public class CashPayment implements PaymentFees {
    @Override
    public double calTotalWithFees(Double total, PaymentMethods paymentMethod) {
        return total;
    }
}
