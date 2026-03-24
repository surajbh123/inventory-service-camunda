package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component("successService")
public class SuccessService implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuccessService.class);

    @Override
    public void execute(DelegateExecution execution) {
        Object interest = execution.getVariable("interestRateTier");
        // set a status variable for downstream usage / auditing
        execution.setVariable("processStatus", "SUCCESS");
        execution.setVariable("approvedOn", Instant.now().toString());
        execution.setVariable("result", "success");

        LOGGER.info("SuccessService executed for processInstanceId={} interestRate={}", execution.getProcessInstanceId(), interest);
    }
}

