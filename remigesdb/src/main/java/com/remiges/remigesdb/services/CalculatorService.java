package com.remiges.remigesdb.services;

import org.springframework.stereotype.Service;

import com.remiges.remigesdb.customexception.InvalidOperationException;
import com.remiges.remigesdb.models.Data;

import lombok.ToString;

@ToString
@Service
public class CalculatorService {

    public Double calculate(Data data) {
        Double result;

        switch (data.getOperator()) { // perform operation based on given operatoer
            case "+" ->
                result = data.getFirstOperand() + data.getSecondOperand();
            case "-" ->
                result = data.getFirstOperand() - data.getSecondOperand();
            case "*" ->
                result = data.getFirstOperand() * data.getSecondOperand();
            case "/" -> {
                if (data.getSecondOperand() == 0) { // Division by zero
                    throw new ArithmeticException("Cannot divide by zero");
                }
                result = data.getFirstOperand() / data.getSecondOperand();
            }
            default -> { // throws Custome Exception if operation is not listed 
                throw new InvalidOperationException("Invalid operator: " + data.getOperator() + ". Use +, -, *, or /.");
            }
        }

        return result;
    }
}
