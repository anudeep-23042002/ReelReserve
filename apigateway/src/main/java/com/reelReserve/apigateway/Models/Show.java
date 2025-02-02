package com.reelReserve.apigateway.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Show")
@NoArgsConstructor
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long ID;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    public Show(LocalDateTime dateTime, long price) {
        this.dateTime = dateTime;
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "price")
    private long price;

    @OneToMany(mappedBy = "show")
    private List<Seat>seatList;
}
