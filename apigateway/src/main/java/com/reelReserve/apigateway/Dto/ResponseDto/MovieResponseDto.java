package com.reelReserve.apigateway.Dto.ResponseDto;

import com.reelReserve.apigateway.Models.Enums.Language;
import lombok.Data;

@Data
public class MovieResponseDto {
    private Long id;
    private String title;
    private String genre;
    private int duration;
    private Language language;
    private String imageUrl;

    public MovieResponseDto(Long id, String title, String genre, int duration, Language language, String imageUrl) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.imageUrl = imageUrl;
    }

    public MovieResponseDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}