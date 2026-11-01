package ru.gr05504.domain;

import java.util.function.DoubleUnaryOperator;

public interface IntegralCalculator {

    double integrate(
            DoubleUnaryOperator f,
            double a,
            double b,
            int n,
            MethodRealised method
    );
}
