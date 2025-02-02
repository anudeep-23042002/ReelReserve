package com.reelReserve.apigateway.Dto.RequestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TheatreDto {

    @NotEmpty(message = "Theatre name should not be null or empty")
    private String name;

    @NotNull(message = "Location Id should not be null")
    private long locationId;

    @NotEmpty(message = "Address should not be null or empty")
    private String address;

}
