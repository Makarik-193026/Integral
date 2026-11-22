package ru.gr05504.domain.strategy;

import ru.gr05504.domain.integrators.IntegratorStrategy;

import java.util.function.DoubleUnaryOperator;

public class TimedIntegrationStrategy implements IntegratorStrategy {

    private final IntegratorStrategy innerStrategy;
    private long elapsedNanoTime;

    public TimedIntegrationStrategy(IntegratorStrategy innerStrategy) {
        if (innerStrategy == null) {
            throw new IllegalArgumentException(
                    "Стратегия интегрирования не может быть null"
            );
        }

        this.innerStrategy = innerStrategy;
    }

    @Override
    public double integralCalculation(
            DoubleUnaryOperator function,
            double a,
            double b,
            int n
    ) {
        long start = System.nanoTime();

        try {
            return innerStrategy.integralCalculation(
                    function, a, b, n
            );
        } finally {
            elapsedNanoTime = System.nanoTime() - start;
        }
    }

    public long getElapsedNanoTime() {
        return elapsedNanoTime;
    }
}