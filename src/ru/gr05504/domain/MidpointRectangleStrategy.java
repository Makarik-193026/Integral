package ru.gr05504.domain;

import ru.gr05504.domain.IntegratorStrategy;

import java.util.function.DoubleUnaryOperator;

public class MidpointRectangleStrategy implements IntegratorStrategy {

    @Override
    public double integralCalculation(
            DoubleUnaryOperator f,
            double a,
            double b,
            int n
    ) {
        double h = (b - a) / n;
        double sum = 0;

        for (int i = 0; i < n; i++) {
            double x = a + (i + 0.5) * h;
            sum += f.applyAsDouble(x);
        }

        return sum * h;
    }
}