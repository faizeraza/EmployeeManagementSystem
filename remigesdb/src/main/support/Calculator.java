package com.assignments.demo.support;

import com.assignments.demo.support.operations.FloatAddition;
import com.assignments.demo.support.operations.FloatDivision;
import com.assignments.demo.support.operations.FloatMultiplication;
import com.assignments.demo.support.operations.FloatSubstraction;

public class Calculator {

    Float firstOperand;
    Float secondOperand;

    public Calculator(Float firstOperand, Float secondOperand) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    public Float add() {
        return new FloatAddition().perform(firstOperand, secondOperand);
    }

    public Float substract() {
        return new FloatSubstraction().perform(firstOperand, secondOperand);
    }

    public Float multiply() {
        return new FloatMultiplication().perform(firstOperand, secondOperand);
    }

    public Float divide() throws ArithmeticException {
        return new FloatDivision().perform(firstOperand, secondOperand);
    }
}
