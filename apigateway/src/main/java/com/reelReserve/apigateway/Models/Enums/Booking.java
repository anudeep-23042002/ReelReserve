package com.reelReserve.apigateway.Models.Enums;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Booking {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long ID;
    private long showID;
    private long seatID;
    private long userID;
    private LocalDateTime bookingTime;
}
