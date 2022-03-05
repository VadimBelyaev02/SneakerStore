package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.OrderDto;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.entity.Order;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderConverter(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Order convertToEntity(OrderDto orderDto) {
        final LocalDate orderedDate = orderDto.getOrderedDate();
        final UUID id = orderDto.getId();
        final Customer customer = Objects.requireNonNull(customerRepository.getById(orderDto.getCustomerId()));
        //final List<UUID> productsIds = orderDto.getProductIds();
        //final List<Product> products = productRepository.findAll().stream()
         //       .filter(product ->  productsIds.contains(product.getId()))
          //      .collect(Collectors.toList());
        return Order.builder()
                .id(id)
                .customer(customer)
                .orderedDate(orderedDate)
            //    .products(products)
                .build();

    }

    public OrderDto convertToDto(Order order) {
        return null;
    }
}
