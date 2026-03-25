package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component("supplierService")
public class SupplierService implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierService.class);

    @Override
    public void execute(DelegateExecution execution) {
        try {
            execution.setVariable("supplierChecked", true);
            execution.setVariable("supplierDecision", "REVIEW");
            execution.setVariable("supplierCheckTimestamp", Instant.now().toString());

            LOGGER.info("SupplierService executed for processInstanceId={}", execution.getProcessInstanceId());

            // Simulate a Kafka error randomly for demonstration purposes
            if (Math.random() < 0.5) { // 50% chance of error
                execution.setVariable("errorMessage", "Simulated Kafka error in SupplierService");
                throw new BpmnError("KAFKA_ERROR", "A Kafka error has occurred.");
            }

        } catch (Exception e) {
            LOGGER.error("Error in SupplierService: {}", e.getMessage());
            throw new BpmnError("KAFKA_ERROR", "A Kafka error has occurred.");
        }
    }
}
