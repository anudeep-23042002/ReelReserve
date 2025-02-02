package com.reelReserve.apigateway.Repo;

import com.reelReserve.apigateway.Models.Location;
import com.reelReserve.apigateway.Models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepo extends JpaRepository<Seat,Long> {

}
