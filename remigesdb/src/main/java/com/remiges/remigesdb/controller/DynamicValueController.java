package com.remiges.remigesdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remiges.remigesdb.dto.Request;
import com.remiges.remigesdb.dto.Response;
import com.remiges.remigesdb.models.PropertyResponse;
import com.remiges.remigesdb.services.DynamicValueService;

@RestController
@RequestMapping()
public class DynamicValueController {

    @Autowired
    DynamicValueService dynamicValueService;

    @PostMapping("/myproperties")
    public ResponseEntity<Response<List<PropertyResponse>>> getProperties(@RequestBody Request<List<String>> request) {
        List<PropertyResponse> responses = dynamicValueService.getDynamicValues(request.getData());
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(responses, request.get_reqid()));
    }
}
