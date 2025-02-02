package com.reelReserve.apigateway.Repo;

import com.reelReserve.apigateway.Models.Location;
import com.reelReserve.apigateway.Models.Seat;
import com.reelReserve.apigateway.Models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowRepo extends JpaRepository<Show,Long> {
    @Query("SELECT s FROM Seat s JOIN s.show sh WHERE sh.id=:showId ORDER BY s.id")
    List<Seat> findSeatsbyShow(@Param("showId") long showId);
}
