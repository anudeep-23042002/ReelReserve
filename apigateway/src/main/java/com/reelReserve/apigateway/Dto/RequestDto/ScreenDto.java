package com.reelReserve.apigateway.Dto.RequestDto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ScreenDto {

    @NotBlank(message = "Screen name must not be empty")
    private String screenName;

    @Min(value = 1, message = "Seat capacity must be at least 1")
    @Max(value = 1000, message = "Seat capacity must not exceed 1000")
    private long seatCapacity;

    @NotBlank(message = "Theatre ID must not be empty")
    private long theatreId;


}
