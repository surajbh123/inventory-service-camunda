package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component("paymentProcess")
public class PaymentProcess implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProcess.class);
    private static final Random RANDOM = new Random();

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("Executing PaymentProcess delegate");

        boolean success = RANDOM.nextBoolean();

        // Generate random data instead of retrieving from execution
        String orderId = UUID.randomUUID().toString();
        double amount = RANDOM.nextDouble() * 1000;
        String[] paymentMethods = {"CREDIT_CARD", "PAYPAL", "BANK_TRANSFER"};
        String paymentMethod = paymentMethods[RANDOM.nextInt(paymentMethods.length)];

        LOGGER.info("Processing payment for order ID: {}", orderId);
        LOGGER.info("Payment Amount: ${}", amount);
        LOGGER.info("Payment Method: {}", paymentMethod);

        // Simulate payment processing logic
        try {
            // Simulate a delay for payment processing
            Thread.sleep(200);

            if (!success) {
                LOGGER.error("Payment processing failed for order ID: {}", orderId);
                throw new BpmnError("ERR_CLEANUP_REQUIRED", "Payment processing failed");
            }

            LOGGER.info("Payment for order {} processed successfully.", orderId);
            execution.setVariable("paymentStatus", "SUCCESS");
        } catch (InterruptedException e) {
            LOGGER.error("Payment processing was interrupted for order ID: {}", orderId, e);
            execution.setVariable("paymentStatus", "FAILED");
            Thread.currentThread().interrupt();
            throw new BpmnError("ERR_CLEANUP_REQUIRED", "Payment processing was interrupted");
        } catch (BpmnError e) {
            throw e; // Re-throw BpmnError to be handled by the process
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred during payment processing for order ID: {}", orderId, e);
            throw new BpmnError("ERR_CLEANUP_REQUIRED", "An unexpected error occurred during payment processing");
        }

        LOGGER.info("PaymentProcess execution completed.");
    }
}
