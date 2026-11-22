package ru.gr05504.application.command;

import ru.gr05504.application.CalculationService;
import ru.gr05504.domain.Calculation;
import ru.gr05504.domain.FunctionDefinition;
import ru.gr05504.domain.metods.MethodRealised;

public class CalculateCommand implements Command {

    private final CalculationService service;
    private final FunctionDefinition definition;
    private final double lower;
    private final double upper;
    private final int intervals;
    private final MethodRealised method;

    private Calculation calculation;

    // Старый конструктор оставляем для совместимости
    public CalculateCommand(
            String formula,
            CalculationService service,
            double lower,
            double upper,
            int intervals,
            MethodRealised method
    ) {
        this(
                FunctionDefinition.builder(formula).build(),
                service,
                lower,
                upper,
                intervals,
                method
        );
    }

    // Новый конструктор принимает FunctionDefinition
    public CalculateCommand(
            FunctionDefinition definition,
            CalculationService service,
            double lower,
            double upper,
            int intervals,
            MethodRealised method
    ) {
        this.definition = definition;
        this.service = service;
        this.lower = lower;
        this.upper = upper;
        this.intervals = intervals;
        this.method = method;
    }

    @Override
    public boolean canExecute() {
        return definition != null
                && service.isFormulaValid(definition)
                && Double.isFinite(lower)
                && Double.isFinite(upper)
                && lower < upper
                && intervals >= 1
                && method != null;
    }

    @Override
    public void execute() {
        if (!canExecute()) {
            throw new IllegalStateException("Некорректные данные для вычисления");
        }

        calculation = service.calculate(
                definition,
                lower,
                upper,
                intervals,
                method
        );
    }

    @Override
    public void undo() {
        if (calculation != null) {
            service.delete(calculation);
            calculation = null;
        }
    }

    public Calculation getCalculation() {
        return calculation;
    }

}