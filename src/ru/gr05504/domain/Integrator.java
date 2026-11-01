package ru.gr05504.domain;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public final class Integrator {

    private Integrator() {
    }

    public static double integrate(
            DoubleUnaryOperator f,
            double a,
            double b,
            int n,
            MethodRealised method
    ) {
        Objects.requireNonNull(f, "Функция не задана");
        Objects.requireNonNull(method, "Метод не выбран");
        Objects.requireNonNull(
                method.strategy(),
                "Стратегия не задана"
        );

        if (!Double.isFinite(a) || !Double.isFinite(b) || a >= b) {
            throw new IllegalArgumentException(
                    "Некорректные пределы интегрирования"
            );
        }

        if (n < 1) {
            throw new IllegalArgumentException(
                    "Число отрезков должно быть не меньше 1"
            );
        }

        double result = method.strategy()
                .integralCalculation(f, a, b, n);

        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException(
                    "Функция не определена или не ограничена на отрезке"
            );
        }

        return result;
    }
}