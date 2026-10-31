package ru.gr05504.domain;

public record MethodRealised(
        String name,
        IntegratorStrategy strategy
) {

    @Override
    public String toString() {
        return name;
    }
}
