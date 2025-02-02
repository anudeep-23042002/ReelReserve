package com.reelReserve.apigateway.Dto.ResponseDto;

import com.reelReserve.apigateway.Models.Screen;
import lombok.Data;

import java.util.List;

@Data
public class TheatreResponseDto {
    private long id;

    private String name;

    private String address;

    private List<ScreenResponseDto> screenList;

    public TheatreResponseDto(long id, String name, String address, List<ScreenResponseDto> screenList) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.screenList = screenList;
    }
}
