package ru.gr05504.infrastructure;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import ru.gr05504.domain.FormulaParser;
import ru.gr05504.domain.FunctionDefinition;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.DoubleUnaryOperator;

public class Exp4jFormulaParser implements FormulaParser {

    @Override
    public DoubleUnaryOperator parse(FunctionDefinition formula) {
        Set<String> variables = new LinkedHashSet<>();

        variables.add(formula.getVariable());
        variables.addAll(formula.getParameters().keySet());

        Expression expression;

        try {
            expression = new ExpressionBuilder(formula.getFormula())
                    .variables(variables)
                    .build();

            formula.getParameters().forEach(
                    expression::setVariable
            );

        } catch (RuntimeException ex) {
            throw new IllegalArgumentException(
                    "Не удалось разобрать формулу: " + ex.getMessage(),
                    ex
            );
        }

        String variable = formula.getVariable();

        return x -> {
            try {
                return expression
                        .setVariable(variable, x)
                        .evaluate();

            } catch (RuntimeException ex) {
                throw new IllegalArgumentException(
                        "Не удалось вычислить формулу в точке "
                                + variable + " = " + x,
                        ex
                );
            }
        };
    }
}