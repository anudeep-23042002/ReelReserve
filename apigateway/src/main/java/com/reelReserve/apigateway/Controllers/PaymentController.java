package com.reelReserve.apigateway.Controllers;

import com.reelReserve.apigateway.Dto.RequestDto.PaymentRequestDto;
import com.reelReserve.apigateway.Services.PaymentService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("payment")
    public ResponseEntity<?> bookingPayment(@RequestBody PaymentRequestDto paymentRequestDto){
        try{
            System.out.println("Entered");
            System.out.println(paymentRequestDto.getPrice());
            String url=paymentService.startPayment(paymentRequestDto);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
