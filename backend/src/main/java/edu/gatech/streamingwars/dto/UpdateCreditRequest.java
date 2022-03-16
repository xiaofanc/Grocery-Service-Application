package edu.gatech.streamingwars.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UpdateCreditRequest {
    @NonNull
    private String customerAccount;
    @NonNull
    private int credit;
}
