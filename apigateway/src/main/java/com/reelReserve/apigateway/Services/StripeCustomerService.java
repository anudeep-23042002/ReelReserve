package com.reelReserve.apigateway.Services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import com.stripe.param.CustomerSearchParams;
import io.github.cdimascio.dotenv.Dotenv;

public class StripeCustomerService {

    static Dotenv dotenv =Dotenv.load();

    public static Customer findCustomer(String email) throws StripeException {
        Stripe.apiKey=dotenv.get("stripe_API_KEY");

        CustomerSearchParams params=CustomerSearchParams.builder().setQuery("email :" + email).build();
        CustomerSearchResult customerSearchResult=Customer.search(params);
        return !customerSearchResult.getData().isEmpty() ? customerSearchResult.getData().get(0) : null;
    }

    public static Customer findorCreateCustomer(String email) throws StripeException {
        Stripe.apiKey=dotenv.get("stripe_SECRET_KEY");
        System.out.println("Stripe api key");
        System.out.println(Stripe.apiKey);
        CustomerListParams params = CustomerListParams.builder()
                .setEmail(email)
                .build();

        try {
            var customers = Customer.list(params);

            if (!customers.getData().isEmpty()) {
                System.out.println("Customer found: " + customers.getData().get(0).getId());
                return customers.getData().getFirst();
            }
            System.out.println("No customer found. Proceeding to create.");
            CustomerCreateParams customerCreateParams= CustomerCreateParams.builder().setEmail(email).build();
            return Customer.create(customerCreateParams);
        } catch (StripeException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
        return null;
    }
}
