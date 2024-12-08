package com.remiges.remigesdb.services;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Add key with default value
    public void addEmployee(String empName) {
        redisTemplate.opsForValue().set("user." + empName, "0");
    }

    // Get key value
    public String getEmployeeValue(String empName) {
        return (String) redisTemplate.opsForValue().get("user." + empName);
    }

    // Increment value
    public void incrementEmployeeValue(String empName) {
        redisTemplate.opsForValue().increment("user." + empName);
    }

    // Decrement value
    public void decrementEmployeeValue(String empName) {
        redisTemplate.opsForValue().decrement("user." + empName);
    }

    public Long updateEmployeeContribution(String department, String empId, int count) {
        String key = "user." + department + "." + empId;

        // Set the key to 0 if it doesn't already exist
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForValue().set(key, "0");
        }

        // Increment the value by count
        return redisTemplate.opsForValue().increment(key, count);
    }

    public String getContribution(String department, String empId) {
        String key = "user." + department + "." + empId, result;
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForValue().set(key, "0");
        }
        result = String.valueOf(redisTemplate.opsForValue().get(key));
        return result;
    }

    // Set TTL for key
    public void setTTL(String empName, long seconds) {
        redisTemplate.expire("user." + empName, Duration.ofSeconds(seconds));
    }

}
