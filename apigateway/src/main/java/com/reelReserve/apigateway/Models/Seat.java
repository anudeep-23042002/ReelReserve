package com.reelReserve.apigateway.Models;

import com.reelReserve.apigateway.Models.Enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Entity
@Table(name = "Seat")
@Data
@NoArgsConstructor
public class Seat {
    public Seat(String seatNumber, Show show, Status status) {
        this.seatNumber = seatNumber;
        this.show = show;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long ID;

    @Column(name = "seat_number")
    private String seatNumber;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private ApplicationUser user;
}
