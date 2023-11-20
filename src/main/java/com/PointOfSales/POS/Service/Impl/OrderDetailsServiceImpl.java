package com.PointOfSales.POS.Service.Impl;

import com.PointOfSales.POS.DTO.AddProductReqDTO;
import com.PointOfSales.POS.DTO.OrderDetailsRequest;
import com.PointOfSales.POS.DTO.OrderDetailsRespDTO;
import com.PointOfSales.POS.Entity.Order;
import com.PointOfSales.POS.Entity.OrderDetails;
import com.PointOfSales.POS.Entity.Product;
import com.PointOfSales.POS.Repository.OrderDetailsRepo;
import com.PointOfSales.POS.Repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl {

    private final OrderDetailsRepo orderDetailsRepo;
    private final ProductServiceImpl productService;
    private final OrderRepo orderRepo;


    public OrderDetailsRespDTO calculateTotal(Integer barcode, Integer qty, String orderNo) {
        Product product = this.productService.getByBarcode(barcode);
        double total = product.getPrice() * qty;

        OrderDetails orderDetails = OrderDetails.builder()
                .price(product.getPrice())
                .qty(qty)
                .total(total)
                .productName(product.getProduct_name())
                .barcode(product.getBarcod())
                .orderNo(orderNo)
                .build();

        this.orderDetailsRepo.save(orderDetails);
        List<OrderDetails> orderDetailsList = this.orderDetailsRepo.findAllByOrderNo(orderNo);

        double finalTotal = orderDetailsList
                .stream()
                .mapToDouble(OrderDetails::getTotal)
                .sum();

        Order order = orderRepo.findByOrderno(orderNo).orElse(null);
        if (order != null) {
            order.setTotal(finalTotal);
            orderRepo.save(order);  // Save the updated Order
        }

        return OrderDetailsRespDTO.builder()
                .orderDetails(orderDetailsList)
                .total(finalTotal)
                .build();
    }

    public Double calOrderTotal(String orderNo) {
        return this.orderDetailsRepo.findAllByOrderNo(orderNo).
                stream()
                .mapToDouble(OrderDetails::getTotal)
                .sum();
    }


    public String deleteOrderDetails(Integer orderDetailsId) {
        try {
            Optional<OrderDetails> orderDetailsOptional = orderDetailsRepo.findById(orderDetailsId);
            if (orderDetailsOptional.isPresent()) {
                OrderDetails orderDetails = orderDetailsOptional.get();
                orderDetailsRepo.delete(orderDetails);
                updateOrderTotal(orderDetails.getOrderNo());
                return "OrderDetails deleted successfully";
            } else {
                return "OrderDetails not found";
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., database errors) as needed
            return "Error deleting OrderDetails";
        }
    }

    // for delete method
    void updateOrderTotal(String orderNo) {
        double finalTotal = calOrderTotal(orderNo);
        Order order = orderRepo.findByOrderno(orderNo).orElse(null);
        if (order != null) {
            order.setTotal(finalTotal);
            orderRepo.save(order);  // Save the updated Order
        }
    }

    public OrderDetails updateOrderDetails(Integer orderDetailsId, OrderDetailsRespDTO dto) {
        try {
            Optional<OrderDetails> orderDetailsOptional = orderDetailsRepo.findById(orderDetailsId);
            if (orderDetailsOptional.isPresent()) {
                OrderDetails existingOrderDetails = orderDetailsOptional.get();

                // Keep the original total for comparison
                double originalTotal = existingOrderDetails.getTotal();

                // Update order detail fields based on the DTO
                if (dto.getPrice() != null) {
                    existingOrderDetails.setPrice(Double.valueOf(dto.getPrice()));
                }
                if (dto.getQty() != null) {
                    existingOrderDetails.setQty(dto.getQty());
                }
                // Recalculate the total for the order detail based on the updated fields
                existingOrderDetails.setTotal(existingOrderDetails.getPrice() * existingOrderDetails.getQty());

                OrderDetails updatedOrderDetails = orderDetailsRepo.save(existingOrderDetails);

                // Recalculate the total for all order details related to the same order
                double newTotal = calculateOrderDetailsTotal(updatedOrderDetails.getOrderNo());

                // If the total has changed, update the Order entity
                if (originalTotal != newTotal) {
                    updateOrderTotal(updatedOrderDetails.getOrderNo());
                }

                return updatedOrderDetails;
            } else {
                // OrderDetails not found
                return null;
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., database errors) as needed
            return null;
        }
    }


    // for update method
    private double calculateOrderDetailsTotal(String orderNo) {
        return orderDetailsRepo.findAllByOrderNo(orderNo)
                .stream()
                .mapToDouble(OrderDetails::getTotal)
                .sum();
    }



    public OrderDetailsRespDTO addOrderDetails(OrderDetailsRequest dto) {
        try {
            Product product = productService.getByBarcode(dto.getBarcod());

            if (product != null) {
                double total = product.getPrice() * dto.getQty();

                OrderDetails orderDetails = OrderDetails.builder()
                        .price(product.getPrice())
                        .qty(dto.getQty())
                        .total(total)
                        .productName(product.getProduct_name())
                        .barcode(product.getBarcod())
                        .orderNo(dto.getOrderno())
                        .build();

                orderDetailsRepo.save(orderDetails);

                // Recalculate the total for all order details related to the same order
                double finalTotal = calculateOrderDetailsTotal(dto.getOrderno());

                // Update the Order entity with the new total
                updateOrderTotal(dto.getOrderno());

                return OrderDetailsRespDTO.builder()
                        .orderDetails(Collections.singletonList(orderDetails))
                        .total(finalTotal)
                        .build();
            } else {
                // Handle the case where the product is not found
                return null;
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., database errors) as needed
            return null;
        }
    }

    public OrderDetails updateOrderDetailss(Integer orderDetailsId, OrderDetailsRespDTO dto) {
        try {
            Optional<OrderDetails> orderDetailsOptional = orderDetailsRepo.findById(orderDetailsId);
            if (orderDetailsOptional.isPresent()) {
                OrderDetails existingOrderDetails = orderDetailsOptional.get();

                // Update order details fields based on the DTO
                if (dto.getPrice() != null) {
                    existingOrderDetails.setPrice(Double.valueOf(dto.getPrice()));
                }
                if (dto.getQty() != null) {
                    existingOrderDetails.setQty(dto.getQty());
                }
                // Add other fields as needed

                OrderDetails updatedOrderDetails = orderDetailsRepo.save(existingOrderDetails);
                return updatedOrderDetails;
            } else {
                // Order details not found
                return null;
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., database errors) as needed
            return null;
        }
    }
}


