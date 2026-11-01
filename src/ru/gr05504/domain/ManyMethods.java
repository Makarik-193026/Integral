package ru.gr05504.domain;

import ru.gr05504.domain.MidpointRectangleStrategy;
import ru.gr05504.domain.SimpsonStrategy;
import ru.gr05504.domain.TrapezoidStrategy;

import java.util.List;

public final class ManyMethods {

    private ManyMethods() {
    }

    public static List<MethodRealised> standard() {
        return List.of(
                new MethodRealised(
                        "Средних прямоугольников",
                        new MidpointRectangleStrategy()
                ),

                new MethodRealised(
                        "Трапеций",
                        new TrapezoidStrategy()
                ),

                new MethodRealised(
                        "Симпсона",
                        new SimpsonStrategy()
                )
        );
    }
}

