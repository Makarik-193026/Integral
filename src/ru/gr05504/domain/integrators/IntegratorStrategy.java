package ru.gr05504.domain.integrators;

import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface IntegratorStrategy {
    double integralCalculation (DoubleUnaryOperator f, double a, double b, int n);
}


