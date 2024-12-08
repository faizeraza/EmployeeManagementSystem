package com.remiges.remigesdb.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Data {

    String operator;
    @NotNull(message = "First Operand can not be null")
    Double firstOperand;
    @NotNull(message = "Second Operand can not be null")
    Double secondOperand;
    Double result;

    @Override
    public String toString() {
        return "firstOperand=" + firstOperand + ", Operator=" + operator + ", secondOperand=" + secondOperand
                + ", result=" + result;
    }
}
