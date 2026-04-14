package com.unibite.unibit_backend.service;

import com.unibite.unibit_backend.dto.BillResponse;
import com.unibite.unibit_backend.entity.*;
import com.unibite.unibit_backend.enums.OrderStatus;
import com.unibite.unibit_backend.enums.PaymentStatus;
import com.unibite.unibit_backend.kafka.OrderProducer;
import com.unibite.unibit_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final OrderProducer orderProducer;


    public BillResponse placeOrder(String email) {

        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("User email is required");
        }

        Cart cart = cartRepository.findByUserEmail(email)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder().userEmail(email).build()
                ));

        List<CartItem> items = cartItemRepository.findByCart(cart);

        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double total = 0;

        Orders orders = orderRepository.save(
                Orders.builder()
                        .userEmail(email)
                        .status(OrderStatus.PENDING)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : items) {

            if (item.getFoodItem() == null) {
                throw new RuntimeException("Invalid food item in cart");
            }

            if (item.getQuantity() <= 0) {
                throw new RuntimeException("Invalid quantity for item: " + item.getFoodItem().getName());
            }

            if (!item.getFoodItem().isAvailable()) {
                throw new RuntimeException(item.getFoodItem().getName() + " is currently unavailable");
            }
            double price = item.getFoodItem().getPrice() * item.getQuantity();
            total += price;

            orderItems.add(orderItemRepository.save(
                    OrderItem.builder()
                            .orders(orders)
                            .foodItem(item.getFoodItem())
                            .quantity(item.getQuantity())
                            .price(price)
                            .build()
            ));
        }

        if (total <= 0) {
            throw new RuntimeException("Total price cannot be zero");
        }

        orders.setTotalPrice(total);
        orderRepository.save(orders);

        Payment payment = paymentRepository.save(
                Payment.builder()
                        .orders(orders)
                        .status(PaymentStatus.PENDING)
                        .build()
        );

        cartItemRepository.deleteAll(items);

        return buildBill(orders, orderItems, payment);
    }


    private BillResponse buildBill(Orders orders, List<OrderItem> items, Payment payment) {

        List<BillResponse.Item> billItems = items.stream()
                .map(i -> BillResponse.Item.builder()
                        .name(i.getFoodItem().getName())
                        .quantity(i.getQuantity())
                        .totalPrice(i.getPrice())
                        .build())
                .toList();

        return BillResponse.builder()
                .orderId(orders.getId())
                .UserEmail(orders.getUserEmail())
                .items(billItems)
                .total(orders.getTotalPrice())
                .paymentStatus(payment.getStatus().name())
                .utr(payment.getUtr())
                .build();
    }


    public Page<Orders> getOrders(int page, int size) {

        if (page < 0) page = 0;
        if (size <= 0) size = 10;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        return orderRepository.findAll(pageable);
    }

    public Orders getById(Long id) {
        if (id == null) {
            throw new RuntimeException("Order ID is required");
        }

        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }


    public Orders updateStatus(Long orderId, OrderStatus status) {

        if (orderId == null) {
            throw new RuntimeException("Order ID is required");
        }

        if (status == null) {
            throw new RuntimeException("Order status is required");
        }

        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!isValidTransition(orders.getStatus(), status)) {
            throw new RuntimeException("Invalid status transition");
        }

        orders.setStatus(status);
        Orders updatedOrder= orderRepository.save(orders);
        orderProducer.sendStatusUpdate(
                updatedOrder.getId(),
                updatedOrder.getStatus().name()
        );
        System.out.println("Order Updated: " + updatedOrder.getId() + " -> " + updatedOrder.getStatus());
        return updatedOrder;
    }


    public List<Orders> getByStatus(OrderStatus status) {

        if (status == null) {
            throw new RuntimeException("Status is required");
        }

        return orderRepository.findByStatus(status);
    }


    public double getTodaySales() {

        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);

        return orderRepository.findByCreatedAtBetween(start, end)
                .stream()
                .mapToDouble(Orders::getTotalPrice)
                .sum();
    }


    private boolean isValidTransition(OrderStatus current, OrderStatus next) {
        return switch (current) {
            case PENDING -> next == OrderStatus.CONFIRMED;
            case CONFIRMED -> next == OrderStatus.PREPARING;
            case PREPARING -> next == OrderStatus.READY;
            case READY -> next == OrderStatus.COMPLETED;
            default -> false;
        };
    }
}