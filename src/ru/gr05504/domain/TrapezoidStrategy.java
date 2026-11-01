package ru.gr05504.domain;

import ru.gr05504.domain.IntegratorStrategy;

import java.util.function.DoubleUnaryOperator;

public class TrapezoidStrategy implements IntegratorStrategy {

    @Override
    public double integralCalculation(
            DoubleUnaryOperator f,
            double a,
            double b,
            int n
    ) {
        double h = (b - a) / n;

        double sum = (
                f.applyAsDouble(a) +
                        f.applyAsDouble(b)
        ) / 2;

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            sum += f.applyAsDouble(x);
        }

        return sum * h;
    }
}
