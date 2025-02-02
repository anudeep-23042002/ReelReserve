package com.reelReserve.apigateway.Services;

import com.reelReserve.apigateway.Dto.RequestDto.LocationDto;
import com.reelReserve.apigateway.Dto.ResponseDto.*;
import com.reelReserve.apigateway.Models.Location;
import com.reelReserve.apigateway.Models.Movie;
import com.reelReserve.apigateway.Models.Theatre;
import com.reelReserve.apigateway.Repo.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepo locationRepo;

    public String addLocation(LocationDto locationDto){
        try {
            if (locationDto.getCity() == null || locationDto.getCity().isEmpty()) {
                throw new IllegalArgumentException("City must not be null or empty.");
            }
            Location location = new Location(locationDto.getCity(), locationDto.getState(), locationDto.getCountry());

            Location savedLocation = locationRepo.save(location);
            return "Location added with ID: " + savedLocation.getID();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MovieResponseDto> getMovies(long locationId) {
        try {
            List<Movie> movieList = locationRepo.findMoviebylocation(locationId);
            return movieList.stream().map(
                    movie -> {
                        return new MovieResponseDto(
                                movie.getID(),
                                movie.getTitle(),
                                movie.getGenre(),
                                movie.getDuration(),
                                movie.getLanguage(),
                                movie.getImageUrl());
                    }).toList();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<LocationResponseDto> getAllLocation() {
        try{
            List<Location> LocationList=locationRepo.findAll();
            return LocationList.stream().map(location -> {
                return new LocationResponseDto(location.getID(),location.getCountry(),location.getState(),location.getCity());
            }).toList();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<TheatreResponseDto> getTheatres(Long locationId) {
        try {
            List<Theatre>theatreList=locationRepo.findTheatreByLocation(locationId);
            return theatreList.stream()
                    .map(theatre -> {
                        List<ScreenResponseDto> screenResponseDtos = theatre.getScreenList().stream()
                                .map(screen -> new ScreenResponseDto(
                                        screen.getID(),
                                        screen.getScreenName(),
                                        0,
                                        null
                                )).toList();
                        return new TheatreResponseDto(
                                theatre.getID(),
                                theatre.getName(),
                                theatre.getAddress(),
                                screenResponseDtos
                        );
                    }).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
