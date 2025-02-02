package com.reelReserve.apigateway.Dto.RequestDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShowDto {
    @NotNull(message = "Date and time must not be null")
    private LocalDateTime dateTime;

    @Min(value = 1, message = "Price must be greater than or equal to 1")
    private long price;

    @NotNull(message = "Movie ID must not be null")
    private Long movieId;

    @NotNull(message = "Screen ID must not be null")
    private Long screenId;
}
