package com.reelReserve.apigateway.Repo;

import com.reelReserve.apigateway.Models.Location;
import com.reelReserve.apigateway.Models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepo extends JpaRepository<Screen,Long> {
}
