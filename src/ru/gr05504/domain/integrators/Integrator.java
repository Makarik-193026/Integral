package ru.gr05504.domain.integrators;

import ru.gr05504.domain.metods.MethodRealised;
import ru.gr05504.domain.strategy.TimedIntegrationStrategy;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public class Integrator implements IntegralCalculator {

    private TimedIntegrationStrategy lastTimedStrategy;

    @Override
    public double integrate(
            DoubleUnaryOperator f,
            double a,
            double b,
            int n,
            MethodRealised method
    ) {
        return integrateWithTiming(f, a, b, n, method).value();
    }

    @Override
    public IntegrationResult integrateWithTiming(
            DoubleUnaryOperator f,
            double a,
            double b,
            int n,
            MethodRealised method
    ) {
        Objects.requireNonNull(f, "Функция не должна быть null");
        Objects.requireNonNull(method, "Метод интегрирования не должен быть null");

        if (!Double.isFinite(a) || !Double.isFinite(b)) {
            throw new IllegalArgumentException("Пределы интегрирования должны быть конечными");
        }

        if (a >= b) {
            throw new IllegalArgumentException("Нижний предел должен быть меньше верхнего");
        }

        if (n < 1) {
            throw new IllegalArgumentException("Количество интервалов должно быть не меньше 1");
        }

        lastTimedStrategy = new TimedIntegrationStrategy(method.strategy());

        double result = lastTimedStrategy.integralCalculation(f, a, b, n);

        if (!Double.isFinite(result)) {
            throw new ArithmeticException("Результат интегрирования не является конечным числом");
        }

        return new IntegrationResult(
                result,
                lastTimedStrategy.getElapsedNanoTime()
        );
    }

    public long getElapsedNanoTime() {
        if (lastTimedStrategy == null) {
            return 0;
        }

        return lastTimedStrategy.getElapsedNanoTime();
    }
}