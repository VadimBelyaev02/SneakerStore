package com.vadim.sneakerstore.dto;

import com.vadim.sneakerstore.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(name = "Photo", description = "Product's photos data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDto {

    @NotNull
    private UUID id;

    @Schema(description = "Link of a photo of product")
    @NotBlank
    private String link;

    @Schema(description = "Id of product that has the photo")
    @NotNull
    private UUID productId;
}
