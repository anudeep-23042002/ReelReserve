package com.reelReserve.apigateway.Controllers;

import com.reelReserve.apigateway.Dto.ResponseDto.LoginDto;
import com.reelReserve.apigateway.Dto.ResponseDto.RefreshResponseDto;
import com.reelReserve.apigateway.JWT.JWTService;
import com.reelReserve.apigateway.Models.ApplicationUser;
import com.reelReserve.apigateway.Models.RefreshToken;
import com.reelReserve.apigateway.Services.RefreshTokenService;
import com.reelReserve.apigateway.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("signup")
    public ResponseEntity<String> signUpController(@RequestBody ApplicationUser user){
        try {
            String result = userService.signUp(user);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Internal Server Error");
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> loginController(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");

            ApplicationUser applicationUser = (ApplicationUser) userService.loadUserByUsername(username);

            if (!userService.checkPassword(password, applicationUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid username or password"));
            }

            String token = jwtService.GenerateToken(username);
            RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(applicationUser);

            return ResponseEntity.ok(new LoginDto(token, refreshToken.getToken(),applicationUser.getRole()));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred"));
        }
    }

    @GetMapping("loguserout")
    public ResponseEntity<?> logoutController(){
        try{
            String result=userService.logout();
            if(result.equals("Success")){
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(500).body("Try Again");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred"));
        }
    }

    @GetMapping("refresh")
    public ResponseEntity<?> refreshController(@RequestParam("refreshToken") String refreshToken) {
        try {
            ApplicationUser applicationUser = refreshTokenService.getUserbytoken(refreshToken);

            System.out.println(applicationUser.getUsername());
            String token = jwtService.GenerateToken(applicationUser.getUsername());

            return ResponseEntity.ok(new RefreshResponseDto(token));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred"));
        }
    }
}
