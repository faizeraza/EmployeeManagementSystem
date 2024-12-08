package com.remiges.remigesdb.support.operations;

//  This class can be used whenever integer addition will be requred 
// Just need to import it 
//  It is the child of ArithmeticOperation as every Arithmetic Operation require two Operands 
// Making a general interface as rule makes sense
public class IntAddition implements ArithmeticOperation<Integer> {
// always have to override perform methode can modefy based on operation we are performing
// takes only integer operands

    @Override
    public Integer perform(Integer firstOperand, Integer secondOperand) {
        return firstOperand + secondOperand;
    }
}
