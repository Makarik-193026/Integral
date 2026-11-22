package ru.gr05504.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class FunctionDefinition {

    private final String formula;
    private final String variable;
    private final Map<String, Double> parameters;

    private FunctionDefinition(Builder builder) {
        this.formula = builder.formula;
        this.variable = builder.variable;

        this.parameters = Collections.unmodifiableMap(
                new LinkedHashMap<>(builder.parameters)
        );
    }

    public String getFormula() {
        return formula;
    }

    public String getVariable() {
        return variable;
    }

    public Map<String, Double> getParameters() {
        return parameters;
    }

    public static Builder builder(String formula) {
        return new Builder(formula);
    }

    public static class Builder {

        private final String formula;
        private String variable = "x";

        private final Map<String, Double> parameters =
                new LinkedHashMap<>();

        private Builder(String formula) {
            if (formula == null || formula.isBlank()) {
                throw new IllegalArgumentException(
                        "Формула не может быть пустой"
                );
            }

            this.formula = formula.trim();
        }

        public Builder variable(String variable) {
            if (variable == null || variable.isBlank()) {
                throw new IllegalArgumentException(
                        "Имя переменной не может быть пустым"
                );
            }

            this.variable = variable.trim();
            return this;
        }

        public Builder parameter(String name, double value) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException(
                        "Имя параметра не может быть пустым"
                );
            }

            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException(
                        "Значение параметра должно быть конечным числом"
                );
            }

            parameters.put(name.trim(), value);
            return this;
        }

        public FunctionDefinition build() {
            return new FunctionDefinition(this);
        }
    }
}
