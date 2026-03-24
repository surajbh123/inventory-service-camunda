package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component("acoountDetails")
public class AcoountDetails implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcoountDetails.class);
    private static final Random RANDOM = new Random();

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("Executing AccountDetails delegate");

        // Generate random data instead of retrieving from execution
        String customerId = UUID.randomUUID().toString();
        String[] accountTypes = {"CHECKING", "SAVINGS", "CREDIT_CARD"};
        String accountType = accountTypes[RANDOM.nextInt(accountTypes.length)];
        double balance = RANDOM.nextDouble() * 5000;

        LOGGER.info("Customer ID: {}", customerId);
        LOGGER.info("Account Type: {}", accountType);
        LOGGER.info("Current Balance: ${}", balance);

        // Simulate checking for a minimum balance
        if (balance < 100) {
            LOGGER.warn("Account balance for customer {} is below the minimum required threshold.", customerId);
            execution.setVariable("accountStatus", "FLAGGED");
        } else {
            LOGGER.info("Account balance is sufficient.");
            execution.setVariable("accountStatus", "OK");
        }

        LOGGER.info("AcoountDetails execution completed.");
    }
}
