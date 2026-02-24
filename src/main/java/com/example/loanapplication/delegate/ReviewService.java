package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component("reviewService")
public class ReviewService implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);

    @Override
    public void execute(DelegateExecution execution) {
        // mark for manual review; set flags/variables
        execution.setVariable("processStatus", "REVIEW_REQUIRED");
        execution.setVariable("reviewRequestedOn", Instant.now().toString());

        LOGGER.info("ReviewService executed for processInstanceId={}", execution.getProcessInstanceId());
    }
}
