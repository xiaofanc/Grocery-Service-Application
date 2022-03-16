package edu.gatech.streamingwars.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UpdatePilotProfileRequest {
    @NonNull
    private String phone;
    @NonNull
    private String tax;
    @NonNull
    private String license;
    @NonNull
    private int experience;
}

