package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.OrderDto;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.entity.Order;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.entity.Stock;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.repository.ProductRepository;
import com.vadim.sneakerstore.repository.StockRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Component
public class OrderConverter {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public OrderConverter(CustomerRepository customerRepository, ProductRepository productRepository, StockRepository stockRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    public Order convertToEntity(OrderDto orderDto) {
        final LocalDate orderedDate = orderDto.getDate();
        final UUID id = orderDto.getId();
        final String status = orderDto.getStatus();
        final String payment = orderDto.getPayment();

        Customer customer = new Customer();
        if (Objects.nonNull(orderDto.getCustomerEmail())) {
            customer = customerRepository.findByEmail(orderDto.getCustomerEmail()).orElseThrow(() ->
                    new NotFoundException("Customer with email = " + orderDto.getCustomerEmail() + " is not found")
            );
        }

        Product product = new Product();
        if (Objects.nonNull(orderDto.getProductId())) {
            product = productRepository.findById(orderDto.getProductId()).orElseThrow(() ->
                    new NotFoundException("Product with id = " + orderDto.getProductId() + " is not found")
            );
        }

        Stock stock = new Stock();
        if (Objects.nonNull(orderDto.getStockId())) {
            stock = stockRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Stock with id = " + orderDto.getStockId() + " is not found")
            );
        }

        return Order.builder()
                .id(id)
                .status(status)
                .product(product)
                .customer(customer)
                .payment(payment)
                .orderedDate(orderedDate)

                .build();
    }

    public OrderDto convertToDto(Order order) {
        final UUID id = order.getId();
        final LocalDate date = order.getOrderedDate();
        final String status = order.getStatus();
        final String payment = order.getPayment();
        final UUID productId = order.getProduct().getId();
        final String email = order.getCustomer().getEmail();

        return OrderDto.builder()
                .id(id)
                .date(date)
                .status(status)
                .productId(productId)
                .payment(payment)
                .customerEmail(email)
                .build();
    }
}
