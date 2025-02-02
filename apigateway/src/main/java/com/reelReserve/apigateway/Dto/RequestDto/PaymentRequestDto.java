package com.reelReserve.apigateway.Dto.RequestDto;

import lombok.Data;

@Data
public class PaymentRequestDto {

    private long SeatId;

    private int price;
}
