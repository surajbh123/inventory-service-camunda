package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;

@Component("reOrderPointService")
public class ReOrderPointService implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReOrderPointService.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        boolean success = new Random().nextBoolean(); // Simulate success
        try {
            // Placeholder delegate logic for Camunda BPMN execution
            execution.setVariable("reOrderPointProcessed", true);
            execution.setVariable("reOrderPointProcessedOn", Instant.now().toString());


            LOGGER.info("ReOrderPointService executed for processInstanceId={}",
                    execution.getProcessInstanceId());

            if (!success) {
                // This triggers the Event Subprocess
                throw new BpmnError("ERR_CLEANUP_REQUIRED", "Failed to process data");
            }
        } catch (Exception e) {
            // Map technical exceptions to BPMN errors if needed
            throw new BpmnError("ERR_CLEANUP_REQUIRED", e.getMessage());
        }
    }
}
