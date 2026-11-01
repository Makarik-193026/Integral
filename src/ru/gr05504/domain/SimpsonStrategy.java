package ru.gr05504.domain;

import ru.gr05504.domain.IntegratorStrategy;

import java.util.function.DoubleUnaryOperator;

public class SimpsonStrategy implements IntegratorStrategy {

    @Override
    public double integralCalculation(
            DoubleUnaryOperator f,
            double a,
            double b,
            int n
    ) {
        if (n % 2 != 0) {
            throw new IllegalArgumentException(
                    "Для метода Симпсона число отрезков должно быть чётным"
            );
        }

        double h = (b - a) / n;

        double sum = f.applyAsDouble(a) + f.applyAsDouble(b);

        for (int i = 1; i < n; i++) {
            double weight = (i % 2 == 1) ? 4 : 2;
            double x = a + i * h;

            sum += weight * f.applyAsDouble(x);
        }

        return sum * h / 3;
    }
}