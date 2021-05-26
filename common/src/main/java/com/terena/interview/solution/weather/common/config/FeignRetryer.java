package com.terena.interview.solution.weather.common.config;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Custom Feign Retryer
 * This will retry 5 times with an interval of 2 seconds if there were any issues with Rest Client connectivity
 */
@Slf4j
public class FeignRetryer implements Retryer {
    private final int maxAttempts;
    private final long backOff;
    int attempt;

    public FeignRetryer() {
        this(2000, 5);
    }

    public FeignRetryer(long backOff, int maxAttempts) {
        this.backOff = backOff;
        this.maxAttempts = maxAttempts;
        int attempt = 1;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        if(attempt++ > maxAttempts) {
            throw e;
        }
        try {
            TimeUnit.MICROSECONDS.sleep(backOff);
        } catch(InterruptedException ex) {

        }
        log.info("Retrying " + e.request().url() + " attempt " + attempt);
    }

    @Override
    public Retryer clone() {
        return new FeignRetryer(backOff, maxAttempts);
    }
}
