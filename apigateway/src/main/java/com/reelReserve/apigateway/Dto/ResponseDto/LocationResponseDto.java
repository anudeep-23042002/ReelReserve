package com.reelReserve.apigateway.Dto.ResponseDto;

import lombok.Data;

@Data
public class LocationResponseDto {
    private long locationId;

    private String country;

    private String state;

    private String city;

    public LocationResponseDto(long locationId, String country, String state, String city) {
        this.locationId=locationId;
        this.country = country;
        this.state = state;
        this.city = city;
    }
}
