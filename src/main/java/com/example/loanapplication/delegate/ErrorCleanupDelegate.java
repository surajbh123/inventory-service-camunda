package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("errorCleanupDelegate")
public class ErrorCleanupDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        String errorMsg = (String) execution.getVariable("errorMessage");
        System.out.println("Cleaning up resources due to: " + errorMsg);

        // Logic: Rollback DB changes, send alert email, or log to ELK
    }
}