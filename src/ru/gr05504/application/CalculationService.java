package ru.gr05504.application;

import ru.gr05504.domain.*;
import ru.gr05504.domain.integrators.IntegralCalculator;
import ru.gr05504.domain.integrators.IntegrationResult;
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

    public void addObserver(CalculationObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CalculationObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (CalculationObserver observer : observers) {
            observer.onCalculationsChanged();
        }
    }

    // Старый метод оставляем для совместимости
    public Calculation calculate(
            String formula,
            double lower,
            double upper,
            int intervals,
            MethodRealised method
    ) {
        FunctionDefinition definition = FunctionDefinition
                .builder(formula)
                .build();

        return calculate(definition, lower, upper, intervals, method);
    }

    // Новый метод принимает объект, созданный Builder
    public Calculation calculate(
            FunctionDefinition definition,
            double lower,
            double upper,
            int intervals,
            MethodRealised method
    ) {
        DoubleUnaryOperator function = parser.parse(definition);

        IntegrationResult integrationResult = integrator.integrateWithTiming(
                function,
                lower,
                upper,
                intervals,
                method
        );

        Calculation calculation = new Calculation(
                definition,
                lower,
                upper,
                intervals,
                method,
                integrationResult.value(),
                integrationResult.elapsedNanoTime()
        );

        repository.save(calculation);
        notifyObservers();

        return calculation;
    }

    public void delete(Calculation calculation) {
        repository.delete(calculation);
        notifyObservers();
    }

    public void restore(Calculation calculation) {
        repository.save(calculation);
        notifyObservers();
    }

    public List<Calculation> getAll() {
        return repository.findAll();
    }

    public boolean isFormulaValid(String formula) {
        try {
            parser.parse(formula);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    // Проверка формулы вместе с переменной и параметрами
    public boolean isFormulaValid(FunctionDefinition definition) {
        try {
            parser.parse(definition);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }
}