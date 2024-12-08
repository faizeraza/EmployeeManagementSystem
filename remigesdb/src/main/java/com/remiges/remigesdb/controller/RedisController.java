package com.remiges.remigesdb.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.remiges.remigesdb.dto.Response;
import com.remiges.remigesdb.dto.UpdateContributionRequest;
import com.remiges.remigesdb.services.RedisService;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/add/{empName}")
    public ResponseEntity<Response<String>> addEmployee(@PathVariable String empName) {
        redisService.addEmployee(empName);
        return ResponseEntity.ok(Response.success("Employee added with default value 0", UUID.randomUUID().toString()));
    }

    @GetMapping("/get/{empName}")
    public ResponseEntity<Response<String>> getEmployeeValue(@PathVariable String empName) {
        return ResponseEntity.ok(Response.success(redisService.getEmployeeValue(empName), UUID.randomUUID().toString()));
    }

    @PostMapping("/increment/{empName}")
    public ResponseEntity<Response<String>> incrementEmployeeValue(@PathVariable String empName) {
        redisService.incrementEmployeeValue(empName);
        return ResponseEntity.ok(Response.success("Incremented employee value", UUID.randomUUID().toString()));
    }

    @PostMapping("/decrement/{empName}")
    public ResponseEntity<Response<String>> decrementEmployeeValue(@PathVariable String empName) {
        redisService.decrementEmployeeValue(empName);
        return ResponseEntity.ok(Response.success("Decremented employee value", UUID.randomUUID().toString()));
    }

    @PostMapping("/ttl/{empName}/{seconds}")
    public ResponseEntity<Response<String>> setTTL(@PathVariable String empName, @PathVariable long seconds) {
        redisService.setTTL(empName, seconds);
        return ResponseEntity.ok(Response.success("TTL set for employee key", UUID.randomUUID().toString()));
    }

    @PostMapping("/updateEmployeeContribution")
    public ResponseEntity<Response<?>> updateEmployeeContribution(
            @RequestBody UpdateContributionRequest request) {

        // Perform Redis key creation/increment
        Long updatedCount = redisService.updateEmployeeContribution(
                request.getDepartmentName(),
                request.getEmployeeId(),
                request.getCount());

        // Prepare response
        String key = "user." + request.getDepartmentName() + "." + request.getEmployeeId();
        Map<String, Object> response = new HashMap<>();
        response.put("key", key);
        response.put("latest_count", updatedCount);

        return ResponseEntity.ok(Response.success(response, UUID.randomUUID().toString()));
    }

    @GetMapping("/getContribution")
    public ResponseEntity<Response<?>> getEmployeeContribution(
            @RequestParam String departmentName,
            @RequestParam String employeeId) {

        try {
            // Retrieve the employee's contribution count
            String contributionCount = redisService.getContribution(departmentName, employeeId);

            Map<String, Object> response = new HashMap<>();
            String key = "user." + departmentName + "." + employeeId;

            if (contributionCount != null) {
                response.put("key", key);
                response.put("latest_count", contributionCount);
            } else {
                response.put("message", "Employee contribution not found for the given department and ID.");
            }

            return ResponseEntity.ok(Response.success(response, UUID.randomUUID().toString()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage(), HttpStatus.CONFLICT.value(), UUID.randomUUID().toString()));
        }
    }
}
