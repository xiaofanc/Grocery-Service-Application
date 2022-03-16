package edu.gatech.streamingwars.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class CancelOrderRequest {
    @NonNull
    private String orderName;
}