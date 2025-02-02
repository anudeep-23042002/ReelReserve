package com.reelReserve.apigateway.Services;

import com.reelReserve.apigateway.Dto.RequestDto.PaymentRequestDto;
import com.reelReserve.apigateway.Models.ApplicationUser;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    UserService userService;

    static Dotenv dotenv = Dotenv.load();

    String url = "http://localhost:3000/";

    public String startPayment(PaymentRequestDto paymentRequestDto) {
        ApplicationUser user = userService.getAuthenticatedUser();
        String email = user.getEmail();
        String name = user.getUsername();

        Customer customer = null;
        try {
            customer = StripeCustomerService.findorCreateCustomer(email);
        } catch (StripeException e) {
            System.err.println("StripeException while finding or creating customer: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error while finding or creating Stripe customer: " + e.getMessage(), e);
        }

        if (customer == null) {
            throw new RuntimeException("Customer could not be created or found.");
        }

        String stripeSecretKey = dotenv.get("stripe_SECRET_KEY");
        if (stripeSecretKey == null || stripeSecretKey.isEmpty()) {
            throw new RuntimeException("Stripe secret key is not set in the environment variables.");
        }

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams.Builder sessionCreateParams = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCustomer(customer.getId())
                .setSuccessUrl(url + "payment-success")
                .setCancelUrl(url + "payment-failure");

        sessionCreateParams.addLineItem(
                SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder().setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .putMetadata("app_id", Long.toString(paymentRequestDto.getSeatId()))
                                                .setName("Seat" + Long.toString(paymentRequestDto.getSeatId())).build()
                                ).setCurrency("usd").setUnitAmount(100L).build()
                        ).build()
        );

        Session session = null;
        try {
            session = Session.create(sessionCreateParams.build());
        } catch (StripeException e) {
            System.err.println("StripeException while creating session: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error while creating Stripe session: " + e.getMessage(), e);
        }

        return session.getUrl();
    }
}
