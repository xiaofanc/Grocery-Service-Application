package edu.gatech.streamingwars.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class OrderAddItemRequest {
    @NonNull
    private String orderName;
    @NonNull
    private String itemName;
    @NonNull
    private int quantity;
    @NonNull
    private int unitPrice;
}
