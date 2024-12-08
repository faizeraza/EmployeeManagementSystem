package com.remiges.remigesdb.support.operations;

// Concerte class for ArithmeticOperation interface as it takes 
// Float Operands and perform substraction
public class FloatSubstraction implements ArithmeticOperation<Float> {

    @Override
    public Float perform(Float firstOperand, Float secondOperand) {
        return firstOperand - secondOperand;
    }
}
