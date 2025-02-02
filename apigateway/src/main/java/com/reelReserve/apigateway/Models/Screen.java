package com.reelReserve.apigateway.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "Screen")
@NoArgsConstructor
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long ID;

    public Screen(String screenName, long seatCapacity) {
        this.screenName = screenName;
        this.seatCapacity = seatCapacity;
    }

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    @Column(name="screen_name")
    private String screenName;

    @Column(name="seat_capacity")
    private long seatCapacity;

    @Column(name="rows")
    private long rows;

    @OneToMany(mappedBy = "screen")
    private List<Show>showList;

}
