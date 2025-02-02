package com.reelReserve.apigateway.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "Theatre")
@NoArgsConstructor
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long ID;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    @Column(name="address")
    private String address;

    @OneToMany(mappedBy = "theatre")
    private List<Screen>screenList;

    public Theatre(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
