package com.reelReserve.apigateway.Services;

import com.reelReserve.apigateway.Dto.RequestDto.ScreenDto;
import com.reelReserve.apigateway.Models.Screen;
import com.reelReserve.apigateway.Models.Theatre;
import com.reelReserve.apigateway.Repo.ScreenRepo;
import com.reelReserve.apigateway.Repo.TheatreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScreenService {

    @Autowired
    private TheatreRepo theatreRepo;

    @Autowired
    private ScreenRepo screenRepo;

    public String addScreen(ScreenDto screenDto) {
        try {
            Optional<Theatre> theatre = theatreRepo.findById(screenDto.getTheatreId());
            if (theatre.isEmpty()) {
                throw new IllegalArgumentException("Theatre is not present");
            }
            Screen screen = new Screen(screenDto.getScreenName(), screenDto.getSeatCapacity());
            screen.setTheatre(theatre.get());

            Screen savedscreen = screenRepo.save(screen);
            return "Screen Created";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
