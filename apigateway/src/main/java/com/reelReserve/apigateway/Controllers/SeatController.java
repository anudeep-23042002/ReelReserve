package com.reelReserve.apigateway.Controllers;


import com.reelReserve.apigateway.Dto.RequestDto.ScreenDto;
import com.reelReserve.apigateway.Dto.RequestDto.SeatDto;
import com.reelReserve.apigateway.Dto.ResponseDto.TheatreResponseDto;
import com.reelReserve.apigateway.Services.ScreenService;
import com.reelReserve.apigateway.Services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController{

    @Autowired
    private SeatService seatService;

    public ResponseEntity<String> addSeat(@RequestBody SeatDto seatDto){
        try {
            String result=seatService.addSeat(seatDto);
            return ResponseEntity.ok().body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }
    @GetMapping("/book/{seatId}")
    public ResponseEntity<String> bookSeat(@PathVariable long seatId){
        try {
            String result =seatService.bookSeat(seatId);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
}

