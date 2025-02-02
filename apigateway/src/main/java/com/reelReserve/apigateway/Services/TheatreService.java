package com.reelReserve.apigateway.Services;

import com.reelReserve.apigateway.Dto.RequestDto.TheatreDto;
import com.reelReserve.apigateway.Models.Location;
import com.reelReserve.apigateway.Models.Theatre;
import com.reelReserve.apigateway.Repo.LocationRepo;
import com.reelReserve.apigateway.Repo.TheatreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepo theatreRepo;

    @Autowired
    private LocationRepo locationRepo;

    public String addTheatre(TheatreDto theatreDto) {
        try {
            Optional<Location> location = locationRepo.findById(theatreDto.getLocationId());
            if (location.isEmpty()) {
                throw new IllegalArgumentException("Location is not present");
            }
            Theatre theatre = new Theatre(theatreDto.getName(), theatreDto.getAddress());
            theatre.setLocation(location.get());

            Theatre savedTheatre = theatreRepo.save(theatre);

            return "Theatre Created";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
