package com.reelReserve.apigateway.Repo;

import com.reelReserve.apigateway.Models.Location;
import com.reelReserve.apigateway.Models.Movie;
import com.reelReserve.apigateway.Models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepo extends JpaRepository<Movie,Long> {
    @Query("SELECT t FROM Theatre t JOIN t.screenList s JOIN s.showList sh JOIN sh.movie m JOIN t.location l WHERE l.id=:locationId AND m.id=:movieId")
    List<Theatre> findTheatresbylocationandmovie(@Param("locationId") long locationId, @Param("movieId") long movieId);
}
