package com.example.loanapplication.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("kafkaErrorHandlerService")
public class KafkaErrorHandlerService implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaErrorHandlerService.class);

    @Override
    public void execute(DelegateExecution execution) {
        LOGGER.info("Handling KAFKA_ERROR for processInstanceId={}", execution.getProcessInstanceId());
        // Add business logic to handle the Kafka error, e.g., retry, log, notify, etc.
        String errorMessage = (String) execution.getVariable("errorMessage");
        LOGGER.error("Received BPMN error KAFKA_ERROR with message: {}", errorMessage);
    }
}

