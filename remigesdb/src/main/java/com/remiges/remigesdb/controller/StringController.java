package com.remiges.remigesdb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.remiges.remigesdb.dto.Response;

import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping()
public class StringController {

    // Mapping EndPoint /hello with getHello methode
    @GetMapping("/hello")
    public ResponseEntity<Response<String>> getHello(@RequestParam("reqID") String id) {
        String message = "Hello World!";
        Response<String> response = Response.success(message, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Mapping EndPoint /hello/{?} with Post methode
    @PostMapping("/hello/{name}")
    public ResponseEntity<Response<String>> postName(@PathVariable @Pattern(regexp = "^[a-zA-Z]+$", message = "not a word") String name, @RequestParam("reqID") String id) {
        String message = "Hello " + name;
        Response<String> response = Response.success(message, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
