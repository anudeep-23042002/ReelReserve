package com.reelReserve.apigateway.Dto.RequestDto;

import com.reelReserve.apigateway.Models.Enums.Language;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class MovieDto {

    @NotBlank(message = "Title must not be empty")
    private String title;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @NotBlank(message = "Description must not be empty")
    private String description;

    @NotBlank(message = "Genre must not be empty")
    private String genre;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Language must not be null")
    private Language language;

    @NotNull(message = "Release date must not be null")
    @FutureOrPresent(message = "Release date must be today or in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    @NotNull(message = "File must not be null")
    private MultipartFile file;
}
