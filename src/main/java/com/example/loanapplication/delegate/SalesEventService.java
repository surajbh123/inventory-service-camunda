package com.example.loanapplication.delegate;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component("salesEventService")
public class SalesEventService implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesEventService.class);

    @Override
    public void execute(DelegateExecution execution) {
        // Mark that the sales event delegate ran
        // create random sales event data and set variables e.g. also randomly decide sales event is going to happen or not
        boolean salesEventHappened = Math.random() < 0.5; // 50% chance of sales event
        execution.setVariable("salesEventHappened", salesEventHappened);
        execution.setVariable("salesEventTimestamp", Instant.now().toString());
        LOGGER.info("SalesEventService executed for processInstanceId={}",
                execution.getProcessInstanceId());
    }
}