package com.reelReserve.apigateway.Repo;

import com.reelReserve.apigateway.Models.Location;
import com.reelReserve.apigateway.Models.Theatre;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TheatreRepo extends JpaRepository<Theatre,Long> {
}
