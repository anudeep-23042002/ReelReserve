package com.reelReserve.apigateway.Dto.ResponseDto;

import lombok.Data;

@Data
public class RefreshResponseDto {
    private String token;

    public RefreshResponseDto(String token) {
        this.token = token;
    }
}
