package com.reelReserve.apigateway.Controllers;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RateLimitConfig {

    private static volatile Bucket bucket=null;

    public Bucket getbucket(){
        if(bucket==null) {
            synchronized (RateLimitConfig.class) {
                if(bucket==null) {
                    Refill refill = Refill.intervally(3, Duration.ofMinutes(1));
                    Bandwidth limit = Bandwidth.classic(3, refill);
                    bucket = Bucket.builder().addLimit(limit).build();
                }
            }
        }
        return bucket;
    }
}
