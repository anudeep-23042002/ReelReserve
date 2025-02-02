package com.reelReserve.apigateway.Models;

import com.reelReserve.apigateway.Models.Enums.Language;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="Movie")
@Data
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long ID;

    @Column(name="title")
    private String title;

    @Column(name="duration")
    private int duration;

    @Column(name = "description")
    private String description;

    @Column(name="genre")
    private String genre;

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name="release_date")
    private Date releaseDate;

    @Column(name="image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "movie")
    private List<Show>showList;

    public Movie(String title, int duration, String description, String genre, Language language, Date releaseDate, String imageUrl) {
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.genre = genre;
        this.language = language;
        this.releaseDate = releaseDate;
        this.imageUrl = imageUrl;
    }
}
