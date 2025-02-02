package com.reelReserve.apigateway.Services;

import com.reelReserve.apigateway.Dto.RequestDto.SeatDto;
import com.reelReserve.apigateway.Models.*;
import com.reelReserve.apigateway.Models.Enums.Status;
import com.reelReserve.apigateway.Repo.SeatRepo;
import com.reelReserve.apigateway.Repo.ShowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    private ShowRepo showRepo;

    @Autowired
    private SeatRepo seatRepo;

    @Autowired
    private UserService userService;

    public String addSeat(SeatDto seatDto) {
        try {
            Optional<Show> show = showRepo.findById(seatDto.getShowId());

            if (show.isEmpty()) {
                throw new IllegalArgumentException("Show is not present");
            }

            Seat seat = new Seat(seatDto.getSeatNumber(), show.get(), seatDto.getStatus());
            Seat savedseat = seatRepo.save(seat);
            return "Seat Created";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String generateSeats(Show show,long seatCapacity, long rows){
        try {
            List<Seat> seats = new ArrayList<>();

            int seatsPerRow = (int) Math.ceil((double) seatCapacity / rows);

            int seatCounter = 0;

            for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
                StringBuilder rowLabel = new StringBuilder();
                int tempIndex = rowIndex;

                while (tempIndex >= 0) {
                    rowLabel.insert(0, (char) ('A' + (tempIndex % 26)));
                    tempIndex = tempIndex / 26 - 1;
                }
                for (int seatNumber = 1; seatNumber <= seatsPerRow; seatNumber++) {
                    if (seatCounter >= seatCapacity) {
                        break;
                    }
                    System.out.println(seatNumber);
                    Seat seat=new Seat(rowLabel.toString(), show, Status.Vacant);
                    Seat savedSeat=seatRepo.save(seat);
                    System.out.println(savedSeat.getSeatNumber());
                    seatCounter++;
                }
            }
            return "Success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String bookSeat(long seatId){
        try {
            Optional<Seat> seatOptional = seatRepo.findById(seatId);
            if (seatOptional.isEmpty()) {
                throw new IllegalArgumentException("seat is not present");
            }
            Seat seat = seatOptional.get();
            if (seat.getStatus() == Status.Booked) {
                return "Seat is Already Booked";
            }
            if (seat.getStatus() == Status.Locked) {
                return "Seat is Locked please try for other seat";
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                throw new IllegalArgumentException("user is not authenticated");
            }
            ApplicationUser user = userService.getAuthenticatedUser();
            seat.setStatus(Status.Booked);
            seat.setUser(user);
            Seat savedSeat = seatRepo.save(seat);
            System.out.println(savedSeat.getStatus());
            return "Seat is booked";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
