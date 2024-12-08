package com.remiges.remigesdb.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.remiges.remigesdb.models.PropertyResponse;

@Service
public class DynamicValueService {

    @Autowired
    Environment env;

    public List<PropertyResponse> getDynamicValues(List<String> identifiers) {
        List<PropertyResponse> values = new ArrayList<>();
        for (String identifier : identifiers) {
            // Fetch the property value using the Environment class, if not found return "NULL"
            String value = env.getProperty(identifier, "NULL");
            values.add(new PropertyResponse(identifier, value));
        }

        return values;
    }
}
