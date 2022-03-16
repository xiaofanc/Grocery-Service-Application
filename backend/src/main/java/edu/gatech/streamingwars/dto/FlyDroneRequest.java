package edu.gatech.streamingwars.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class FlyDroneRequest {
    @NonNull
    private String droneId;
    @NonNull
    private String pilotAccount;
}
