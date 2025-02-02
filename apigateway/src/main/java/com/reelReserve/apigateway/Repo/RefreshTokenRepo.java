package com.reelReserve.apigateway.Repo;

import com.reelReserve.apigateway.Models.ApplicationUser;
import com.reelReserve.apigateway.Models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByUserId(long id);

    Optional<RefreshToken> findBytoken(String token);
}
