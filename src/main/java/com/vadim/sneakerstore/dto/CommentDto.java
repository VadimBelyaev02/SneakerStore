package com.vadim.sneakerstore.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Comment", description = "Transfer comment data")
@Builder
@Data
public class CommentDto {

    private UUID id;

    @Schema(description = "Comment message")
    @NotBlank
    private String message;

    @Schema(description = "Number of points")
    @NotNull
    private Integer rate;

    @Schema(description = "Product's id which is commented")
    @NotNull
    private UUID productId;

    @Schema(description = "When was commented")
    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate date;

    @Schema(description = "Full name who commented a product")
    @NotBlank
    private String customer;

    @Schema(description = "Man's id who commented")
    @NotNull
    private UUID customerId;
}
