package com.example.loanapplication.delegate;

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
		execution.setVariable("supplierChecked", true);
		execution.setVariable("supplierDecision", "REVIEW");
		execution.setVariable("supplierCheckTimestamp", Instant.now().toString());

		LOGGER.info("SupplierService executed for processInstanceId={}", execution.getProcessInstanceId());
	}
}
