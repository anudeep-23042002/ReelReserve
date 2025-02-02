package com.reelReserve.apigateway.Dto.ResponseDto;

import com.reelReserve.apigateway.Models.Enums.Status;
import lombok.Data;

@Data
public class seatResponseDto {

    private long seatId;

    private String seatNumber;

    private Status status;

    public seatResponseDto(long seatId, String seatNumber, Status status) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.status = status;
    }
}
