package ru.gr05504.domain.integrators;

import ru.gr05504.domain.metods.MethodRealised;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public class Integrator implements IntegralCalculator {

    @Override
    public double integrate(
            DoubleUnaryOperator f,
            double a,
            double b,
            int n,
            MethodRealised method
    ) {
        Objects.requireNonNull(f, "Функция не должна быть null");
        Objects.requireNonNull(method, "Метод интегрирования не должен быть null");
        Objects.requireNonNull(
                method.strategy(),
                "Стратегия интегрирования не должна быть null"
        );

        if (!Double.isFinite(a) || !Double.isFinite(b)) {
            throw new IllegalArgumentException(
                    "Границы интегрирования должны быть конечными числами"
            );
        }

        if (a >= b) {
            throw new IllegalArgumentException(
                    "Левая граница должна быть меньше правой"
            );
        }

        if (n < 1) {
            throw new IllegalArgumentException(
                    "Количество интервалов должно быть положительным"
            );
        }

        double result = method.strategy()
                .integralCalculation(f, a, b, n);

        if (!Double.isFinite(result)) {
            throw new ArithmeticException(
                    "Результат интегрирования не является конечным числом"
            );
        }

        return result;
    }
}