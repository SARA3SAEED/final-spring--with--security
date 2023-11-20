package com.PointOfSales.POS.util.payment;

public class CreditCardPayment implements PaymentFees{
    @Override
    public double calTotalWithFees(Double total, PaymentMethods paymentMethod) {
        return total + (total * 0.5);
    }
}
