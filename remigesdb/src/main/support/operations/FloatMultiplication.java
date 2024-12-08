package com.assignments.demo.support.operations;

/**
 * FloatMultiplication class implements the ArithmeticOperation interface to
 * perform multiplication on two Float values.
 */
public class FloatMultiplication implements ArithmeticOperation<Float> {

    /**
     * Multiplies two Float operands and returns the result.
     *
     * @param firstOperand the first float value to be multiplied.
     * @param secondOperand the second float value to be multiplied.
     * @return the product of the two operands as a Float.
     */
    @Override
    public Float perform(Float firstOperand, Float secondOperand) {
        return firstOperand * secondOperand;
    }
}
