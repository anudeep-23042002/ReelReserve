package com.reelReserve.apigateway.Controllers;

import com.reelReserve.apigateway.Dto.RequestDto.TheatreDto;
import com.reelReserve.apigateway.Models.ApplicationUser;
import com.reelReserve.apigateway.Models.Enums.Role;
import com.reelReserve.apigateway.Services.TheatreService;
import com.reelReserve.apigateway.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/theatre")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addTheatre(@RequestBody TheatreDto theatreDto){
        try{
            ApplicationUser user=userService.getAuthenticatedUser();
            if(user.getRole().equals(Role.ADMIN)) {
                String result=theatreService.addTheatre(theatreDto);
                return ResponseEntity.ok(result);
            }
            else{
                return ResponseEntity.status(403).body("User does not have permission for this operation");
            }
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
