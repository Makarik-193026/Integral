package ru.gr05504.domain;

import ru.gr05504.domain.metods.MethodRealised;

import java.util.Map;

public class Calculation {

    private final FunctionDefinition functionDefinition;
    private final double lower;
    private final double upper;
    private final int intervals;
    private final String method;
    private final double result;

    // Старый конструктор оставляем для совместимости
    public Calculation(
            String formula,
            double lower,
            double upper,
            int intervals,
            MethodRealised method,
            double result
    ) {
        this(
                FunctionDefinition.builder(formula).build(),
                lower,
                upper,
                intervals,
                method,
                result
        );
    }

    // Новый конструктор принимает результат Builder
    public Calculation(
            FunctionDefinition functionDefinition,
            double lower,
            double upper,
            int intervals,
            MethodRealised method,
            double result
    ) {
        this.functionDefinition = functionDefinition;
        this.lower = lower;
        this.upper = upper;
        this.intervals = intervals;
        this.method = method.toString();
        this.result = result;
    }

    public FunctionDefinition getFunctionDefinition() {
        return functionDefinition;
    }

    public String getFormula() {
        return functionDefinition.getFormula();
    }

    public String getVariable() {
        return functionDefinition.getVariable();
    }

    public Map<String, Double> getParameters() {
        return functionDefinition.getParameters();
    }

    public double getLower() {
        return lower;
    }

    public double getUpper() {
        return upper;
    }

    public int getIntervals() {
        return intervals;
    }

    public String getMethod() {
        return method;
    }

    public double getResult() {
        return result;
    }
}