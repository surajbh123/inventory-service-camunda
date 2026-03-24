package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component("successService")
public class SuccessService implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuccessService.class);

    @Override
    public void execute(DelegateExecution execution) {
        Object interest = execution.getVariable("interestRateTier");
        // set a status variable for downstream usage / auditing
        execution.setVariable("processStatus", "SUCCESS");
        execution.setVariable("approvedOn", Instant.now().toString());
        execution.setVariable("result", createSuccessResponse());

        LOGGER.info("SuccessService executed for processInstanceId={} interestRate={}", execution.getProcessInstanceId(), interest);
    }

    // create success response variable
    private Map<String, Object> createSuccessResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("interestRateTier", "Tier 1"); // Placeholder value, can be dynamic based on logic
        response.put("timestamp", Instant.now().toString());
        return response;
    }

}

