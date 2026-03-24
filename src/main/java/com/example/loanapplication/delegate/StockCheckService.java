package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component("stockCheckDelegate")
public class StockCheckService implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockCheckService.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
            LOGGER.info("checking Stock limit");
            // create Random number to simulate stock check
            int stockCheckResult = (int) (Math.random() * 100);
            // create random threshold for stock check
            int stockThreshold = 50;
            // set variable based on stock check result
            if (stockCheckResult > stockThreshold) {
                execution.setVariable("stockCheckPassed", true);
                LOGGER.info("Stock check passed with result: {}", stockCheckResult);
            } else {
                execution.setVariable("stockCheckPassed", false);
                LOGGER.info("Stock check failed with result: {}", stockCheckResult);
            }

    }
}
