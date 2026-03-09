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
    }
}
