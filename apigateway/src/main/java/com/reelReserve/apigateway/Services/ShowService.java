package com.reelReserve.apigateway.Services;

import com.reelReserve.apigateway.Dto.RequestDto.ScreenDto;
import com.reelReserve.apigateway.Dto.RequestDto.ShowDto;
import com.reelReserve.apigateway.Dto.ResponseDto.seatResponseDto;
import com.reelReserve.apigateway.Models.Movie;
import com.reelReserve.apigateway.Models.Screen;
import com.reelReserve.apigateway.Models.Seat;
import com.reelReserve.apigateway.Models.Show;
import com.reelReserve.apigateway.Repo.MovieRepo;
import com.reelReserve.apigateway.Repo.ScreenRepo;
import com.reelReserve.apigateway.Repo.ShowRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepo showRepo;

    @Autowired
    private ScreenRepo screenRepo;

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private SeatService seatService;

    public String addShow(ShowDto showDto) {
        try {
            Optional<Screen> screenOptional = screenRepo.findById(showDto.getScreenId());
            Optional<Movie> movieOptional = movieRepo.findById(showDto.getMovieId());

            if (screenOptional.isEmpty()) {
                throw new IllegalArgumentException("Screen is not present");
            }
            if (movieOptional.isEmpty()) {
                throw new IllegalArgumentException("Movie is not present");
            }

            Screen screen = screenOptional.get();
            Show show = new Show(showDto.getDateTime(), showDto.getPrice());
            show.setScreen(screen);
            show.setMovie(movieOptional.get());
            Show savedshow = showRepo.save(show);
            String SeatUpdateStatus = seatService.generateSeats(savedshow, screen.getSeatCapacity(), screen.getRows());

            return "Show Created";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<seatResponseDto> getSeats(long showId){
        try {
            List<Seat> seatList = showRepo.findSeatsbyShow(showId);
            return seatList.stream().map(seat -> {
                        return
                                new seatResponseDto(seat.getID(),
                                        seat.getSeatNumber(),
                                        seat.getStatus());
                    })
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
