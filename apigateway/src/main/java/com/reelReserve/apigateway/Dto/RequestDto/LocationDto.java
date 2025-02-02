package com.reelReserve.apigateway.Dto.RequestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocationDto {

    @NotBlank(message = "City must not be empty")
    private String city;

    @NotBlank(message = "State must not be empty")
    private String state;

    @NotBlank(message = "Country must not be empty")
    private String country;
}
