package com.reelReserve.apigateway.Repo;

import com.reelReserve.apigateway.Models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<ApplicationUser,Long>{
    Optional<ApplicationUser> findByName(String name);
}
