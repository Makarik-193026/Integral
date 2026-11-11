package ru.gr05504.application;

import ru.gr05504.domain.*;
import ru.gr05504.domain.integrators.IntegralCalculator;
import ru.gr05504.domain.metods.MethodRealised;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class CalculationService {

    private final CalculationRepository repository;
    private final FormulaParser parser;
    private final IntegralCalculator integrator;

    private final List<CalculationObserver> observers = new ArrayList<>();

    public CalculationService(
            CalculationRepository repository,
            FormulaParser parser,
            IntegralCalculator integrator
    ) {
        this.repository = repository;
        this.parser = parser;
        this.integrator = integrator;
    }

    // Подписка наблюдателя
    public void addObserver(CalculationObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    // Отписка наблюдателя
    public void removeObserver(CalculationObserver observer) {
        observers.remove(observer);
    }

    // Уведомление всех наблюдателей
    private void notifyObservers() {
        for (CalculationObserver observer : List.copyOf(observers)) {
            observer.onCalculationsChanged();
        }
    }

    // Разбирает формулу, вычисляет интеграл и сохраняет расчёт
    public Calculation calculate(
            String formula,
            double lower,
            double upper,
            int intervals,
            MethodRealised method
    ) {
        DoubleUnaryOperator f = parser.parse(formula);

        double result = integrator.integrate(
                f, lower, upper, intervals, method
        );

        Calculation calculation = new Calculation(
                formula, lower, upper, intervals, method, result
        );

        repository.save(calculation);

        notifyObservers();

        return calculation;
    }

    public void delete(Calculation calculation) {
        repository.delete(calculation);
        notifyObservers();
    }

    public List<Calculation> getAll() {
        return repository.findAll();
    }
}


