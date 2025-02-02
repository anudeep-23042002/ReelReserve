package com.reelReserve.apigateway.Services;

import com.reelReserve.apigateway.Models.ApplicationUser;
import com.reelReserve.apigateway.Repo.UserRepo;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Aspect
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @Autowired
    RefreshTokenService refreshTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> userOptional=userRepo.findByName(username);
        if(userOptional.isEmpty()){
            System.out.println("Username not found");
            throw new UsernameNotFoundException("Username not found: " + username);
        }
        ApplicationUser user=userOptional.get();
        System.out.println("Username");
        System.out.println("User Seats: " + (user.getSeatList() == null ? "null" : "loaded"));
        System.out.println(user.getUsername());
        return user;
    }

    public String signUp(ApplicationUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        ApplicationUser savedUser=userRepo.save(user);
        return "User Saved";
    }
    public ApplicationUser getAuthenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        return userRepo.findByName(userDetails.getUsername()).get();
    }
    public boolean checkPassword(String password,String userPassword){
        return encoder.matches(password,userPassword);
    }

    public String logout() {
        try{
            ApplicationUser user=getAuthenticatedUser();
            return refreshTokenService.deleteRefreshToken(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
