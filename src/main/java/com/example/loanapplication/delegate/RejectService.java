package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component("rejectService")
public class RejectService implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RejectService.class);

    @Override
    public void execute(DelegateExecution execution) {
        execution.setVariable("processStatus", "REJECTED");
        execution.setVariable("rejectedOn", Instant.now().toString());

        LOGGER.info("RejectService executed for processInstanceId={}", execution.getProcessInstanceId());
    }
}
