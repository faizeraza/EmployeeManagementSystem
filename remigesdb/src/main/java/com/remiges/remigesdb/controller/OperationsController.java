package com.remiges.remigesdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remiges.remigesdb.dto.Request;
import com.remiges.remigesdb.dto.Response;
import com.remiges.remigesdb.models.Data;
import com.remiges.remigesdb.models.DataList;
import com.remiges.remigesdb.services.CalculatorService;
import com.remiges.remigesdb.services.StatisticalOperationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping()
public class OperationsController {

    // Instantiating the classes with @Autowired annotation
    // spring will take care of initalization 
    @Autowired
    private CalculatorService calculatorService;
    @Autowired
    private StatisticalOperationService statisticalOperationService;

    @PostMapping("/mysum") // endpoint will perform arithmetic ops
    public ResponseEntity<Response<Data>> claculator(@Valid @RequestBody Request<Data> request) {
        try {
            Data data = request.getData();
            data.setResult(calculatorService.calculate(data));
            return ResponseEntity.status(HttpStatus.OK).body(Response.success(data, request.get_reqid()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(Response.failure(e.getMessage(), HttpStatus.EXPECTATION_FAILED.value(), request.get_reqid()));
        }
    }

    @PostMapping("/mysumList") //endpoint will perform statistical ops
    public ResponseEntity<Response<DataList>> postStatisticalOperation(@Valid @RequestBody Request<DataList> request) {
        DataList data = request.getData();
        try {
            data.setResult(statisticalOperationService.execute(data));
            return ResponseEntity.status(HttpStatus.OK).body(Response.success(data, request.get_reqid()));
        } catch (Exception e) {
            data.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(Response.failure(e.getMessage(), HttpStatus.EXPECTATION_FAILED.value(), request.get_reqid()));
        }
    }
}
