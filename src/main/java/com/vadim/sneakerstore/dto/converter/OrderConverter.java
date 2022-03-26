package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.OrderDto;
import com.vadim.sneakerstore.entity.Order;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class OrderConverter {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderConverter(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Order convertToEntity(OrderDto orderDto) {
        final LocalDate orderedDate = orderDto.getDate();
        final UUID id = orderDto.getId();
        final String status = orderDto.getStatus();
        final String payment = orderDto.getPayment();
        final Integer amount = orderDto.getAmount();
        final UUID groupId = orderDto.getGroupId();
        final Product product = productRepository.findById(orderDto.getProductId()).orElseThrow(() ->
                new NotFoundException("Product with id = " + orderDto.getProductId() + " is not found")
        );

        return Order.builder()
                .id(id)
                .status(status)
                .product(product)
                .payment(payment)
                .groupId(groupId)
                .orderedDate(orderedDate)
                .amount(amount)
                .build();

    }

    public OrderDto convertToDto(Order order) {
        final UUID id = order.getId();
        final LocalDate date = order.getOrderedDate();
        final String status = order.getStatus();
        final String payment = order.getPayment();
        final Integer amount = order.getAmount();
        final UUID groupId = order.getGroupId();
        final UUID productId = order.getProduct().getId();


        return OrderDto.builder()
                .id(id)
                .date(date)
                .status(status)
                .productId(productId)
                .payment(payment)
                .groupId(groupId)
                .amount(amount)
                .build();
    }
}
