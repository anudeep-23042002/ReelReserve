package com.reelReserve.apigateway.Dto.ResponseDto;

import com.reelReserve.apigateway.Models.Screen;
import lombok.Data;

import java.util.List;


@Data
public class ScreenResponseDto {
    private long id;
    private String screenName;
    private long seatCapacity;
    List<ShowResponseDto> showResponseDtoList;

    public ScreenResponseDto(long id, String screenName, long seatCapacity, List<ShowResponseDto> showResponseDtoList) {
        this.id = id;
        this.screenName = screenName;
        this.seatCapacity = seatCapacity;
        this.showResponseDtoList = showResponseDtoList;
    }
}
