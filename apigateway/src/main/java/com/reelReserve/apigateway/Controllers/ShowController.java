package com.reelReserve.apigateway.Controllers;

import com.reelReserve.apigateway.Dto.RequestDto.ShowDto;
import com.reelReserve.apigateway.Dto.ResponseDto.seatResponseDto;
import com.reelReserve.apigateway.Models.ApplicationUser;
import com.reelReserve.apigateway.Models.Enums.Role;
import com.reelReserve.apigateway.Services.ShowService;
import com.reelReserve.apigateway.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShowController {
    
    @Autowired
    private ShowService showService;

    @Autowired
    private UserService userService;

    @PostMapping("/show")
    public ResponseEntity<String> addShow(@RequestBody ShowDto showDto){
        try{
            ApplicationUser user=userService.getAuthenticatedUser();
            System.out.println(user.getRole());
            if(user.getRole().equals(Role.ADMIN)) {
                String result=showService.addShow(showDto);
                return ResponseEntity.ok(result);
            }
            else{
                return ResponseEntity.status(403).body("User does not have permission for this operation");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(500).body("Error : "+e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/show/getseats/{showId}")
    public ResponseEntity<List<seatResponseDto>> getSeats(@PathVariable long showId){
        try{
            List<seatResponseDto>seatResponseDtos=showService.getSeats(showId);
            return ResponseEntity.ok(seatResponseDtos);
        }catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }



}
