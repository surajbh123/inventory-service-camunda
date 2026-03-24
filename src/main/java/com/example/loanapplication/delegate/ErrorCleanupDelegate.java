package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("errorCleanupDelegate")
public class ErrorCleanupDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorCleanupDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        String errorMsg = (String) execution.getVariable("errorMessage");
        LOGGER.error("Cleaning up resources due to: {}", errorMsg);
        execution.setVariable("result", "Error: " + errorMsg);
        // Logic: Rollback DB changes, send alert email, or log to ELK
    }
}