package com.vadim.sneakerstore.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.entity.Product;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderDto {

    private UUID id;

    @NotBlank
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate orderedDate;

    @NotBlank
    private String status;

    @NotBlank
    private UUID customerId;

    private List<UUID> productIds;
}
