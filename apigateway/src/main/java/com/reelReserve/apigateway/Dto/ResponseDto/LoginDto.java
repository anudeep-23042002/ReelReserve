package com.reelReserve.apigateway.Dto.ResponseDto;

import com.reelReserve.apigateway.Models.Enums.Role;
import lombok.Data;

@Data
public class LoginDto {

    private String token;

    private String refreshToken;

    private Role role;

    public LoginDto(String token, String refreshToken, Role role) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.role=role;
    }
}
