package com.reelReserve.apigateway.Dto.RequestDto;

import com.reelReserve.apigateway.Models.Enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SeatDto {
    @NotNull(message = "Seat number must not be null")
    @Size(min = 1, max = 10, message = "Seat number must be between 1 and 10 characters")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Seat number must contain only alphanumeric characters")
    private String seatNumber;

    @NotNull(message = "Show ID must not be null")
    private Long showId;

    @NotNull(message = "Status must not be null")
    private Status status;

}
