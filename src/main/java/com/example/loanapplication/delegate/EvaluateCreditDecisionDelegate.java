package com.example.loanapplication.delegate;

import org.camunda.bpm.dmn.engine.DmnDecisionRuleResult;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("evaluateCreditDecisionDelegate")
public class EvaluateCreditDecisionDelegate implements JavaDelegate {

    private final DecisionService decisionService;

    public EvaluateCreditDecisionDelegate(org.camunda.bpm.engine.DecisionService decisionService) {
        this.decisionService = decisionService;
    }

    @Override
    public void execute(DelegateExecution execution) {
        // collect inputs
        Number creditScore = (Number) execution.getVariable("creditScore");
        Number debtToIncomeRatio = (Number) execution.getVariable("debtToIncomeRatio");
        String existingDefaults = (String) execution.getVariable("existingDefaults");
        Number loanAmountRatio = (Number) execution.getVariable("loanAmountRatio");

        VariableMap variables = Variables.createVariables()
                .putValue("creditScore", creditScore)
                .putValue("debtToIncomeRatio", debtToIncomeRatio)
                .putValue("existingDefaults", existingDefaults)
                .putValue("loanAmountRatio", loanAmountRatio);

        DmnDecisionTableResult result = decisionService.evaluateDecisionTableByKey("Decision_079285x", variables);

        if (result == null || result.isEmpty()) {
            execution.setVariable("decision", "REVIEW");
            return;
        }

        // take first matched rule
        DmnDecisionRuleResult first = result.get(0);

        // Retrieve values using .get() which returns the raw Object (String, Double, etc.)
        String decision = (String) first.get("decision");
        Double interest = (Double) first.get("interestRateTier");

        execution.setVariable("decision", decision);
        execution.setVariable("interestRateTier", interest);
    }
}
