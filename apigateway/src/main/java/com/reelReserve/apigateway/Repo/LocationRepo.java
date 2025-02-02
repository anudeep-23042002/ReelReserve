package com.reelReserve.apigateway.Repo;

import com.reelReserve.apigateway.Models.Location;
import com.reelReserve.apigateway.Models.Movie;
import com.reelReserve.apigateway.Models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepo extends JpaRepository<Location,Long> {

    @Query("SELECT m FROM Movie m JOIN m.showList sh JOIN sh.screen s JOIN s.theatre t JOIN t.location l WHERE l.id=:locationId")
    List<Movie> findMoviebylocation(@Param("locationId") long locationId);

    @Query("SELECT t FROM Theatre t JOIN t.location l WHERE l.id=:locationId")
    List<Theatre> findTheatreByLocation(@Param("locationId") long locationId);
}
