package com.vadim.sneakerstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortingProduct {
    private int page;
    private int size;
}
