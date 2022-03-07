package com.vadim.sneakerstore.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class CommentDto {

    private UUID id;

    @NotBlank
    private String message;

    @NotNull
    private Integer rate;

    @NotNull
    private UUID productId;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate date;

    @NotBlank
    private String customer;

    @NotNull
    private UUID customerId;
}
