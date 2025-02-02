package com.reelReserve.apigateway.Controllers;


import com.reelReserve.apigateway.Dto.RequestDto.ScreenDto;
import com.reelReserve.apigateway.Dto.RequestDto.TheatreDto;
import com.reelReserve.apigateway.Models.ApplicationUser;
import com.reelReserve.apigateway.Models.Enums.Role;
import com.reelReserve.apigateway.Services.ScreenService;
import com.reelReserve.apigateway.Services.TheatreService;
import com.reelReserve.apigateway.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScreenController {

    @Autowired
    private ScreenService screenService;

    @Autowired
    private UserService userService;

    @PostMapping("/screen")
    public ResponseEntity<String> addScreen(@RequestBody ScreenDto screenDto)
    {
        try {
            ApplicationUser user=userService.getAuthenticatedUser();
            if(user.getRole().equals(Role.ADMIN)) {
                String result=screenService.addScreen(screenDto);
                return ResponseEntity.ok().body(result);
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
}

