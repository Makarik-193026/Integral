package ru.gr05504.domain;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

public final class ManyMethods {
    private ManyMethods(){};

    public static List<MethodRealised> standard(){
        return List.of(
                new MethodRealised("Средних прямоугольников",
                        (f, a, b, n) -> {
                    double h = (b - a) / n;                 // длина одного отрезка разбиения
                    double sum = 0;
                    for (int i = 0; i < n; i++) {
                        sum += f.applyAsDouble(a + (i + 0.5) * h);
                    }
                    sum *= h;
                    return sum;
                }),
                new MethodRealised("Трапеций", (f, a, b, n) -> {
                    double h = (b - a) / n;                 // длина одного отрезка разбиения
                    double sum = (f.applyAsDouble(a) + f.applyAsDouble(b)) / 2;
                    for (int i = 1; i < n; i++) {
                        sum += f.applyAsDouble(a + i * h);
                    }
                    sum *= h;
                    return sum;
                }),
                new MethodRealised("Симпсона", (f, a, b, n) -> {
                    double h = (b - a) / n;                 // длина одного отрезка разбиения
                    double  sum = f.applyAsDouble(a) + f.applyAsDouble(b);                // длина одного отрезка разбиения
                    if (n % 2 != 0)
                        throw new IllegalArgumentException("Для метода Симпсона число отрезков должно быть чётным");
                    // Узлы с нечётным номером весят 4, внутренние с чётным номером весят 2
                    sum = f.applyAsDouble(a) + f.applyAsDouble(b);
                    for (int i = 1; i < n; i++) {
                        double weight = (i % 2 == 1) ? 4 : 2;
                        sum += weight * f.applyAsDouble(a + i * h);
                    }
                    sum *= h / 3;
                    return sum;
                })
        );

    }
}

