package com.reelReserve.apigateway.Controllers;

import com.reelReserve.apigateway.Dto.RequestDto.LocationDto;
import com.reelReserve.apigateway.Dto.ResponseDto.LocationResponseDto;
import com.reelReserve.apigateway.Dto.ResponseDto.MovieResponseDto;
import com.reelReserve.apigateway.Dto.ResponseDto.TheatreResponseDto;
import com.reelReserve.apigateway.Models.ApplicationUser;
import com.reelReserve.apigateway.Models.Enums.Role;
import com.reelReserve.apigateway.Services.LocationService;
import com.reelReserve.apigateway.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    @PostMapping("location")
    public ResponseEntity<String> addLocation(@RequestBody LocationDto locationDto){
        try{
            ApplicationUser user=userService.getAuthenticatedUser();
            if(user.getRole().equals(Role.ADMIN)) {
                String result = locationService.addLocation(locationDto);
                return ResponseEntity.ok(result);
            }
            else{
                return ResponseEntity.status(403).body("User does not have permission for this operation");
            }
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/location")
    public ResponseEntity<?> getAllLocations(){
        try{
            List<LocationResponseDto> locationResponseDtoList=locationService.getAllLocation();
            return ResponseEntity.ok(locationResponseDtoList);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("location/{locationId}/theatres")
    public ResponseEntity<List<TheatreResponseDto>> getTheatres(@PathVariable Long locationId){
        try {
            List<TheatreResponseDto> theatres = locationService.getTheatres(locationId);
            return ResponseEntity.ok(theatres);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("location/{locationId}")
    public ResponseEntity<List<MovieResponseDto>> getMovies(@PathVariable Long locationId){
        try {
            List<MovieResponseDto> movies = locationService.getMovies(locationId);
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

















}
