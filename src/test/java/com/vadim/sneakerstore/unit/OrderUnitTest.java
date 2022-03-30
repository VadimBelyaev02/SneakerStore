package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.dto.OrderDto;
import com.vadim.sneakerstore.dto.converter.OrderConverter;
import com.vadim.sneakerstore.entity.Card;
import com.vadim.sneakerstore.entity.Order;
import com.vadim.sneakerstore.repository.OrderRepository;
import com.vadim.sneakerstore.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import com.vadim.sneakerstore.entity.Picture;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.repository.PictureRepository;
import com.vadim.sneakerstore.service.impl.CustomerServiceImpl;
import com.vadim.sneakerstore.service.impl.PictureServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.Ordered;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class OrderUnitTest {

    @InjectMocks
    private OrderServiceImpl service;

    @Mock
    private OrderRepository repository;

    @Mock
    private OrderConverter converter;

    @Test
    public void shouldReturnOrderDtoById() {
        UUID id = UUID.randomUUID();
        Order order = new Order();
        OrderDto orderDto = new OrderDto();

        when(repository.findById(id)).thenReturn(Optional.of(order));
        when(converter.convertToDto(order)).thenReturn(orderDto);

        assertEquals(service.getById(id), orderDto);

        verify(repository, only()).findById(id);
        verify(converter, only()).convertToDto(order);
    }

    @Test
    public void shouldThrowsNotFoundException() {
        UUID id = UUID.randomUUID();
        Order order = new Order();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(id));

        verify(repository, only()).findById(id);
        verify(converter, never()).convertToDto(order);
    }

    @Test
    public void shouldReturnListOfOrderDtos() {
        Order order = new Order();
        OrderDto orderDto = new OrderDto();
        List<OrderDto> orderDtos = Stream.of(orderDto, orderDto, orderDto)
                .collect(Collectors.toList());
        List<Order> orders = Stream.of(order, order, order)
                .collect(Collectors.toList());

        when(repository.findAll()).thenReturn(orders);
        when(converter.convertToDto(order)).thenReturn(orderDto);

        assertEquals(service.getAll(), orderDtos);

        verify(repository, only()).findAll();
        verify(converter, times(3)).convertToDto(order);
    }

    @Test
    public void shouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        assertEquals(service.getAll(), new ArrayList<>());

        verify(repository, only()).findAll();
        verify(converter, never()).convertToDto(new Order());
    }

    @Test
    public void shouldReturnSavedOrderDto() {
        UUID id = UUID.randomUUID();
        Order order = new Order();
        OrderDto orderDto = new OrderDto();

        when(repository.existsById(id)).thenReturn(false);
        when(repository.save(order)).thenReturn(order);
        when(converter.convertToDto(order)).thenReturn(orderDto);
        when(converter.convertToEntity(orderDto)).thenReturn(order);

        assertEquals(service.save(orderDto), orderDto);

        verify(repository, times(1)).save(order);
        verify(repository, times(1)).existsById(id);
        verify(converter, times(1)).convertToDto(order);
        verify(converter, times(1)).convertToEntity(orderDto);
    }

    @Test
    public void shouldThrowsAlreadyExistsExceptionWhileSaving() {
        UUID id = UUID.randomUUID();
        Order order = new Order();
        OrderDto orderDto = new OrderDto();

        when(repository.existsById(id)).thenReturn(false);
        when(repository.save(order)).thenReturn(order);
        when(converter.convertToDto(order)).thenReturn(orderDto);
        when(converter.convertToEntity(orderDto)).thenReturn(order);

        assertEquals(service.save(orderDto), orderDto);

        verify(repository, never()).save(order);
        verify(repository, times(1)).existsById(id);
        verify(converter, never()).convertToDto(order);
        verify(converter, never()).convertToEntity(orderDto);
    }

    @Test
    public void shouldReturnUpdatedOrderDto() {

    }

    @Test
    public void shouldThrowsNotFoundExceptionWhileUpdating() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.update(new OrderDto()));

        verify(repository, only()).existsById(id);
        verify(converter, never()).convertToEntity(new OrderDto());
        verify(converter, never()).convertToDto(new Order());
    }

    @Test
    public void shouldThrowsNotFoundExceptionWhileDeleting() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.deleteById(id));

        verify(repository, only()).existsById(id);
    }

    @Test
    public void shouldReturnAllOrderDtosByCustomerId() {
        UUID customerId = UUID.randomUUID();
        Order order = new Order();
        OrderDto orderDto = new OrderDto();
        List<Order> orders = Stream.of(order, order, order)
                        .collect(Collectors.toList());
        List<OrderDto> orderDtos = Stream.of(orderDto, orderDto, orderDto)
                        .collect(Collectors.toList());

        when(repository.findAllByCustomerId(customerId)).thenReturn(orders);
        when(converter.convertToEntity(orderDto)).thenReturn(order);

        assertEquals(service.getAllByCustomerId(customerId), orderDtos);

        verify(repository, only()).findAllByCustomerId(customerId);
        verify(converter, times(3)).convertToDto(order);
    }


}
