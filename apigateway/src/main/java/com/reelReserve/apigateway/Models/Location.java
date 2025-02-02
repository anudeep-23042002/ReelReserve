package com.reelReserve.apigateway.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long ID;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="country")
    private String country;

    @OneToMany(mappedBy = "location")
    private List<Theatre> theatreList;

    public Location(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }
}
