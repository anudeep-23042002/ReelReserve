package com.reelReserve.apigateway.Dto.ResponseDto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowResponseDto {
    private long Id;
    private LocalDateTime dateTime;

    public ShowResponseDto(long id, LocalDateTime dateTime) {
        Id = id;
        this.dateTime = dateTime;
    }
}
