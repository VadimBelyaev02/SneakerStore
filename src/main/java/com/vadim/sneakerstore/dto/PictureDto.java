package com.vadim.sneakerstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(name = "Photo", description = "Product's photos data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PictureDto {

    @Schema(description = "Link of a photo of product")
    @NotBlank
    private String link;

    @Schema(description = "Id of product that has the photo")
    @NotNull
    private UUID productId;
}
