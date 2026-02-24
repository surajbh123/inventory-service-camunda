package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component("approveService")
public class ApproveService implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApproveService.class);

    @Override
    public void execute(DelegateExecution execution) {
        Object interest = execution.getVariable("interestRateTier");
        // set a status variable for downstream usage / auditing
        execution.setVariable("processStatus", "APPROVED");
        execution.setVariable("approvedOn", Instant.now().toString());

        LOGGER.info("ApproveService executed for processInstanceId={} interestRate={}", execution.getProcessInstanceId(), interest);
    }
}
