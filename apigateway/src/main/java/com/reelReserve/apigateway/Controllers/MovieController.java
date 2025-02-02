package com.reelReserve.apigateway.Controllers;

import com.reelReserve.apigateway.Dto.RequestDto.MovieDto;
import com.reelReserve.apigateway.Dto.ResponseDto.MovieResponseDto;
import com.reelReserve.apigateway.Dto.ResponseDto.TheatreResponseDto;
import com.reelReserve.apigateway.Models.ApplicationUser;
import com.reelReserve.apigateway.Models.Enums.Role;
import com.reelReserve.apigateway.Services.CloudinaryService;
import com.reelReserve.apigateway.Services.MovieService;
import com.reelReserve.apigateway.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private UserService userService;

    @PostMapping("movie")
    public ResponseEntity<String> addMovie(@ModelAttribute MovieDto movieDto) {
        try {
            ApplicationUser user=userService.getAuthenticatedUser();
            if(user.getRole().equals(Role.ADMIN)) {
                System.out.println(movieDto.getTitle());
                String url=cloudinaryService.uploadImage(movieDto.getFile());
                System.out.println(url);
                String result = movieService.addMovie(movieDto,url);
                return ResponseEntity.ok(result);
            }
            else{
                return ResponseEntity.status(403).body("User does not have permission for this operation");
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }
    @GetMapping("movie")
    public ResponseEntity<?> getMovies(){
        try{
            List<MovieResponseDto>movieResponseDtos=movieService.getMovies();
            return ResponseEntity.ok(movieResponseDtos);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("movie/{movieId}")
    public ResponseEntity<?> getMovieById(@PathVariable long movieId){
        try{
            MovieResponseDto movieDto=movieService.getMovieById(movieId);
            return ResponseEntity.ok(movieDto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/movie/{movieId}/{locationId}")
    public ResponseEntity<List<TheatreResponseDto>> getTheatreAndLocationAndMovie(@PathVariable long movieId, @PathVariable long locationId){
        try {
            List<TheatreResponseDto>theatreResponseDtos =movieService.gettheatresbylocationandmovie(movieId, locationId);
            return ResponseEntity.ok(theatreResponseDtos);
        }catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

}
