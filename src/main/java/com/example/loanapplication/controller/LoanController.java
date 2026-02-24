package com.example.loanapplication.controller;

import com.example.loanapplication.model.Applicant;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoanController {

    private final RuntimeService runtimeService;
    private final DecisionService decisionService;

    public LoanController(RuntimeService runtimeService, DecisionService decisionService) {
        this.runtimeService = runtimeService;
        this.decisionService = decisionService;
    }

    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestBody Applicant applicant) {
        // prepare variables for DMN evaluation
        VariableMap variables = Variables.createVariables()
                .putValue("creditScore", applicant.getCreditScore())
                .putValue("debtToIncomeRatio", applicant.getDebtToIncomeRatio())
                .putValue("existingDefaults", applicant.getExistingDefaults())
                .putValue("loanAmountRatio", applicant.getLoanAmountRatio());

        // evaluate DMN directly so we can return a synchronous response to the caller
        DmnDecisionTableResult result = decisionService.evaluateDecisionTableByKey("CreditDecision", variables);

        String decision = "REVIEW";
        String interestRateTier = null;

        if (result != null && !result.isEmpty()) {
            Map<String, Object> first = result.get(0);
            Object dec = first.get("decision");
            Object ir = first.get("interestRateTier");
            if (dec != null) decision = dec.toString();
            if (ir != null) interestRateTier = ir.toString();
        }

        // start the BPMN process with the evaluated results so background processing can continue
        Map<String, Object> vars = new HashMap<>();
        vars.put("creditScore", applicant.getCreditScore());
        vars.put("debtToIncomeRatio", applicant.getDebtToIncomeRatio());
        vars.put("existingDefaults", applicant.getExistingDefaults());
        vars.put("loanAmountRatio", applicant.getLoanAmountRatio());
        vars.put("decision", decision);
        vars.put("interestRateTier", interestRateTier);

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("loan_process", vars);

        // build a simple synchronous response
        Map<String, Object> response = new HashMap<>();
        response.put("processInstanceId", pi != null ? pi.getId() : null);
        response.put("decision", decision);
        response.put("interestRateTier", interestRateTier);
        response.put("message", "Process started and decision evaluated");

        return ResponseEntity.ok(response);
    }
}
