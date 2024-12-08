package com.remiges.remigesdb.services;

import org.springframework.stereotype.Service;

import com.remiges.remigesdb.customexception.InvalidOperationException;
import com.remiges.remigesdb.models.DataList;
import com.remiges.remigesdb.support.StatisticalOperations;

@Service
public class StatisticalOperationService {

    public String execute(DataList data) {

        StatisticalOperations statOps = new StatisticalOperations(data.getDataList());
        String operation = data.getOperator();
        String result;

        // The program should understand if user will put max insted of maximum
        if (operation.contains("max")) {

            operation = "maximum";

        } // The program should understand if user will put min insted of minimum
        else if (operation.contains("min")) {

            operation = "minimum";

        }

        switch (operation) { // set thye result vlue based on asked operation

            case "count" ->
                result = String.valueOf(statOps.getCount());

            case "mean" ->
                result = String.valueOf(statOps.getMean());

            case "maximum" ->
                result = String.valueOf(statOps.getMaximum());

            case "minimum" ->
                result = String.valueOf(statOps.getMinimum());

            case "sort" ->
                result = String.valueOf(statOps.sortList());
            default ->
                throw new InvalidOperationException("Enter Valid Operation");   // throws error if operation is not listed

        }
        return result;
    }

}
