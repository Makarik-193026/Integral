package ru.gr05504.domain.integrators;

import ru.gr05504.domain.metods.MethodRealised;

import java.util.function.DoubleUnaryOperator;

public interface IntegralCalculator {

    double integrate(
            DoubleUnaryOperator f,
            double a,
            double b,
            int n,
            MethodRealised method
    );

    default IntegrationResult integrateWithTiming(
            DoubleUnaryOperator f,
            double a,
            double b,
            int n,
            MethodRealised method
    ) {
        long start = System.nanoTime();

        double value = integrate(f, a, b, n, method);

        long elapsed = System.nanoTime() - start;

        return new IntegrationResult(value, elapsed);
    }
}
