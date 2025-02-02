package com.reelReserve.apigateway.Services;

import com.reelReserve.apigateway.Models.ApplicationUser;
import com.reelReserve.apigateway.Models.RefreshToken;
import com.reelReserve.apigateway.Repo.RefreshTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private long refreshTokenDurationMs=60000;

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    public RefreshToken createOrUpdateRefreshToken(ApplicationUser user) {
        if (user == null) {
            throw new IllegalArgumentException("Invalid user: User cannot be null and must have a valid ID.");
        }

        Optional<RefreshToken> existingToken = refreshTokenRepo.findByUserId(user.getId());

        RefreshToken refreshToken;
        if (existingToken.isPresent()) {
            refreshToken = existingToken.get();
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());
        } else {
            refreshToken = new RefreshToken();
            refreshToken.setUser(user);
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());
        }

        return refreshTokenRepo.save(refreshToken);
    }

    public ApplicationUser getUserbytoken(String refreshtoken) {
        Optional<RefreshToken>optionalRefreshToken=refreshTokenRepo.findBytoken(refreshtoken);

        if(optionalRefreshToken.isEmpty()){
            System.out.println("Invalid refresh Token");
            throw new RuntimeException("Invalid Token");
        }
        return optionalRefreshToken.get().getUser();
    }
    public String deleteRefreshToken(ApplicationUser user){
        try {
            if (user == null) {
                throw new IllegalArgumentException("Invalid user: User cannot be null and must have a valid ID.");
            }
            Optional<RefreshToken> existingToken = refreshTokenRepo.findByUserId(user.getId());

            if (existingToken.isPresent()) {
                RefreshToken refreshToken = existingToken.get();
                refreshToken.setExpiryDate(Instant.now().minusMillis(refreshTokenDurationMs));
                refreshToken.setToken(null);
                refreshTokenRepo.save(refreshToken);
            } else {
                System.out.println("User is already logged out");
            }
            return "Success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
