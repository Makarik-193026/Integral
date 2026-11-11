package ru.gr05504.domain.strategy;

import ru.gr05504.domain.integrators.IntegratorStrategy;

import java.util.function.DoubleUnaryOperator;

public class SimpsonStrategy implements IntegratorStrategy {

    @Override
    public double integralCalculation(
            DoubleUnaryOperator f,
            double a,
            double b,
            int n
    ) {
        double h = (b - a) / n;

        // Если количество отрезков нечётное,
        // последний отрезок вычисляем методом трапеций
        int simpsonIntervals = (n % 2 == 0) ? n : n - 1;

        double sum = f.applyAsDouble(a)
                + f.applyAsDouble(a + simpsonIntervals * h);

        for (int i = 1; i < simpsonIntervals; i++) {
            double weight = (i % 2 == 1) ? 4 : 2;
            double x = a + i * h;

            sum += weight * f.applyAsDouble(x);
        }

        double result = sum * h / 3;

        // Для нечётного n добавляем последний отрезок
        // по формуле метода трапеций
        if (n % 2 != 0) {
            double x = a + simpsonIntervals * h;
            double nextX = b;

            result += h * (
                    f.applyAsDouble(x) +
                            f.applyAsDouble(nextX)
            ) / 2;
        }

        return result;
    }
}