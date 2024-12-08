package com.remiges.remigesdb.support.operations;

/**
 * FloatDivision class implements the ArithmeticOperation interface to perform
 * division on two Float values.
 */
public class FloatDivision implements ArithmeticOperation<Float> {

    /**
     * Divides the first Float operand by the second Float operand.
     *
     * @param firstOperand the numerator (dividend) in the division operation.
     * @param secondOperand the denominator (divisor) in the division operation.
     * @return the quotient of the two operands as a Float, or null if division
     * by zero is attempted.
     */
    @Override
    public Float perform(Float firstOperand, Float secondOperand) throws ArithmeticException {
        if (secondOperand == 0) { // Check for division by zero
            throw new ArithmeticException("Error: Division by zero is not allowed.");
        }
        return firstOperand / secondOperand; // Perform the division and return the result
    }
}
