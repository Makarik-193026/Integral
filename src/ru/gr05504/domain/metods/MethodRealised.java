package ru.gr05504.domain.metods;

import ru.gr05504.domain.integrators.IntegratorStrategy;

public record MethodRealised(
        String name,
        IntegratorStrategy strategy
) {

    @Override
    public String toString() {
        return name;
    }
}
