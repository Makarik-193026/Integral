// domain/FormulaParser.java
package ru.gr05504.domain;

import java.util.function.DoubleUnaryOperator;

// Превращает текст формулы в функцию одной переменной x.
// При ошибке разбора бросает IllegalArgumentException
public interface FormulaParser {

    DoubleUnaryOperator parse(FunctionDefinition formula);

    default DoubleUnaryOperator parse(String formula) {
        FunctionDefinition definition = FunctionDefinition
                .builder(formula)
                .build();

        return parse(definition);
    }
}