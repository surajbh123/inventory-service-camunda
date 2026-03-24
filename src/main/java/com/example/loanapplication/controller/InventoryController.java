package com.example.loanapplication.controller;

import com.example.loanapplication.model.Applicant;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {


    private final RuntimeService runtimeService;
    private final DecisionService decisionService;

    public InventoryController(RuntimeService runtimeService, DecisionService decisionService) {
        this.runtimeService = runtimeService;
        this.decisionService = decisionService;
    }


    @PostMapping("/check")
    public ResponseEntity<String> checkInventory() {

        Map<String, Object> vars = new HashMap<>();
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Process_0pswi9n",vars);
        return ResponseEntity.ok("success");

    }
}
