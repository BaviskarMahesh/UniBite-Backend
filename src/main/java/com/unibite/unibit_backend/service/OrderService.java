package com.unibite.unibit_backend.service;

import com.unibite.unibit_backend.dto.BillResponse;
import com.unibite.unibit_backend.entity.*;
import com.unibite.unibit_backend.enums.OrderStatus;
import com.unibite.unibit_backend.enums.PaymentStatus;
import com.unibite.unibit_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;

    public BillResponse placeOrder(String email){
        Cart cart=cartRepository.findByUserEmail(email).orElseThrow();
        List<CartItem> items=cartItemRepository.findByCart(cart);
        double total=0;
        Orders orders=orderRepository.save(
                Orders.builder()
                        .userEmail(email)
                        .status(OrderStatus.PENDING)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        List<OrderItem> orderItems=new ArrayList<>();

        for(CartItem item:items){
            double price=item.getFoodItem().getPrice()*item.getQuantity();
            total+=price;
            orderItems.add(orderItemRepository.save(
                    OrderItem.builder()
                            .orders(orders)
                            .foodItem(item.getFoodItem())
                            .quantity(item.getQuantity())
                            .price(price)
                            .build()
            ));
        }
        orders.setTotalPrice(total);
        orderRepository.save(orders);

        Payment payment=paymentRepository.save(
                Payment.builder()
                                .orders(orders)
                                        .status(PaymentStatus.PENDING)

                        .build()
        );
        cartItemRepository.deleteAll(items);
        return buildBill(orders,orderItems,payment);
    }
    private BillResponse buildBill(Orders orders,List<OrderItem> items,Payment payment){
        List<BillResponse.Item> billItems=items.stream()
                .map(i->BillResponse.Item.builder()
                        .name(i.getFoodItem().getName())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .build()).toList();

        return BillResponse.builder()
                .orderId(orders.getId())
                .UserEmail(orders.getUserEmail())
                .items(billItems)
                .total(orders.getTotalPrice())
                .paymentStatus(payment.getStatus().name())
                .utr(payment.getUtr())
                .build();


    }
}
