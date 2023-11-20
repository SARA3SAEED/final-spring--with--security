package com.PointOfSales.POS.Service.Impl;


import com.PointOfSales.POS.DTO.UpdateOrderReqDTO;
import com.PointOfSales.POS.Entity.Order;
import com.PointOfSales.POS.Entity.OrderStatus;
import com.PointOfSales.POS.Repository.OrderRepo;
import com.PointOfSales.POS.Repository.ProductRepo;
import com.PointOfSales.POS.util.payment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl {

    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final OrderDetailsServiceImpl orderDetailsService;

    public Order saveOrder(Order order) {
        return orderRepo.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order getOrderById(Integer orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }


//    public Order completeOrder(String orderNo, PaymentMethods paymentMethod) {
//        Optional<Order> byOrderno = this.orderRepo.findByOrderno(orderNo);
//        Double total = this.orderDetailsService.calOrderTotal(orderNo);
//        PaymentFees payment = null;
//        if (paymentMethod == PaymentMethods.CASH) {
//            payment = new CashPayment();
//        } else if (paymentMethod == PaymentMethods.CREDIT_CARD) {
//            payment = new CreditCardPayment();
//        }
//        double totalWithFees = payment.calTotalWithFees(total, paymentMethod);
//
//        byOrderno.get().setOrderStatus(OrderStatus.Completed);
//        byOrderno.get().setTotal(totalWithFees);
//        return this.orderRepo.save(byOrderno.get());
//    }


    public Order completeOrder(String orderNo, PaymentMethods paymentMethod) {
        Optional<Order> byOrderno = this.orderRepo.findByOrderno(orderNo);

        if (byOrderno.isPresent()) {
            Order order = byOrderno.get();
            Double total = this.orderDetailsService.calOrderTotal(orderNo);

            // Handle the case when the PaymentMethods enum does not match any known case
            PaymentFees payment = null;
            switch (paymentMethod) {
                case CASH:
                    payment = new CashPayment();
                    break;
                case CREDIT_CARD:
                    payment = new CreditCardPayment();
                    break;
                // Handle other payment methods if needed
                default:
                    throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
            }

            // Calculate total with fees
            double totalWithFees = payment.calTotalWithFees(total, paymentMethod);

            // Update order status and total
            order.setOrderStatus(OrderStatus.Completed);
            order.setTotal(totalWithFees);

            // Save the order in a transaction
            return this.orderRepo.save(order);
        }
        return null;
    }


    public String deleteOrder(Integer Id) {
        try {
            Optional<Order> orderOptional = orderRepo.findById(Id);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                orderRepo.delete(order);
                return "Order deleted successfully";
            } else {
                return "Order not found";
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., database errors) as needed
            return "Error deleting order";
        }
    }


    public Order updateOrder(Integer orderId, UpdateOrderReqDTO dto) {
        try {
            Optional<Order> orderOptional = orderRepo.findById(orderId);
            if (orderOptional.isPresent()) {
                Order existingOrder = orderOptional.get();

                // Update order fields based on the DTO
                if (dto.getOrderStatus() != null) {
                    existingOrder.setOrderStatus(dto.getOrderStatus());
                }
                // Add other fields as needed

                Order updatedOrder = orderRepo.save(existingOrder);

                // Recalculate the total for the order details related to the updated order
                orderDetailsService.updateOrderTotal(updatedOrder.getOrderno());

                return updatedOrder;
            } else {
                // Order not found
                return null;
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., database errors) as needed
            return null;
        }
    }

}
