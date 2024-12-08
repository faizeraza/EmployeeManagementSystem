package com.remiges.remigesdb.support.operations;

public class FloatAddition implements ArithmeticOperation<Float> {

    @Override
    public Float perform(Float firstOperand, Float secondOperand) {
        return firstOperand + secondOperand;
    }
}
