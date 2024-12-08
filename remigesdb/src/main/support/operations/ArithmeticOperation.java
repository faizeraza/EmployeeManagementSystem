package com.assignments.demo.support.operations;

// The General Way of creating any Arithmetic Operation
// we will leave type of it generic its user should define it
// Can be used for float , int , Double does not matter for this rule
public interface ArithmeticOperation<T extends Number> {

    // First They always have two operands to perform
    // and they always get performed 
    T perform(T firstOperand, T secondOperand);
}
