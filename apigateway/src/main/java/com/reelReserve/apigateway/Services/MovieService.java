package com.reelReserve.apigateway.Services;

import com.reelReserve.apigateway.Dto.RequestDto.MovieDto;
import com.reelReserve.apigateway.Dto.ResponseDto.MovieResponseDto;
import com.reelReserve.apigateway.Dto.ResponseDto.ScreenResponseDto;
import com.reelReserve.apigateway.Dto.ResponseDto.ShowResponseDto;
import com.reelReserve.apigateway.Dto.ResponseDto.TheatreResponseDto;
import com.reelReserve.apigateway.Models.Movie;
import com.reelReserve.apigateway.Models.Theatre;
import com.reelReserve.apigateway.Repo.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private MovieRepo movieRepo;

    @Autowired
    public void addMovieRepo(MovieRepo movieRepo){this.movieRepo=movieRepo;}


    public String addMovie(MovieDto movieDto, String url) {
        try {
            Movie movie = new Movie(movieDto.getTitle(), movieDto.getDuration(), movieDto.getDescription(),
                    movieDto.getGenre(), movieDto.getLanguage(), movieDto.getReleaseDate(),url);
            Movie savedmovie = movieRepo.save(movie);
            return "Added Movie";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<TheatreResponseDto> gettheatresbylocationandmovie(long movieId, long locationId) {
        try {
            List<Theatre> theatreList = movieRepo.findTheatresbylocationandmovie(locationId, movieId);
            return theatreList.stream()
                    .map(theatre -> {
                        List<ScreenResponseDto> screenResponseDtos = theatre.getScreenList().stream()
                                .map(screen -> new ScreenResponseDto(
                                        screen.getID(),
                                        screen.getScreenName(),
                                        screen.getSeatCapacity(),
                                        screen.getShowList().stream()
                                                .map(show -> new ShowResponseDto(
                                                        show.getID(),
                                                        show.getDateTime()
                                                )).toList()
                                )).toList();
                        return new TheatreResponseDto(
                                theatre.getID(),
                                theatre.getName(),
                                theatre.getAddress(),
                                screenResponseDtos
                        );
                    }).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MovieResponseDto> getMovies() {
        try{
            List<Movie> movies=movieRepo.findAll();
            return movies.stream().map(movie -> {
                return new MovieResponseDto(movie.getID(),movie.getTitle());
            }).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public MovieResponseDto getMovieById(long movieId) {
        Optional<Movie> movieOptional=movieRepo.findById(movieId);
        if(movieOptional.isEmpty()){
            throw new RuntimeException("No movie found with this id");
        }
        Movie movie=movieOptional.get();
        return new MovieResponseDto(movie.getID(),movie.getTitle(),movie.getGenre(),movie.getDuration(),
                movie.getLanguage(),movie.getImageUrl());
    }
}
