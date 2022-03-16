package edu.gatech.streamingwars.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PurchaseOrderRequest {
    @NonNull
    private String orderName;
}
